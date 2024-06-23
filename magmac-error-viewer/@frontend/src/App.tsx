import axios from "axios";
import React, {useEffect, useState} from "react";

function countDigits(index: number) {
    return Math.floor(index === 0 ? 0 : Math.log10(index)) + 1;
}

function format(index: number, length: number) {
    const number = countDigits(index);
    const totalLength = countDigits(length);
    return " ".repeat(totalLength - number) + index;
}

type Element = React.JSX.Element;

function Card({label, children}: { label: string, children: Element }) {
    return (
        <div style={{
            width: "100%",
            height: "100%"
        }}>
            <div style={{padding: "1rem"}}>
                {label}
            </div>
            <hr/>
            <div style={{
                padding: "1rem",
                height: "calc(100% - (2 * 1rem))"
            }}>
                {children}
            </div>
        </div>
    );
}

const ROOT = "http://localhost:3000";

interface TreeNode {
    $?: { [key: string]: string };

    [key: string]: any;
}

function createElementFromTreeNode(tree: TreeNode): Element {
    if (!tree) return <></>;

    const children = Object.keys(tree).map((key) => {
        if (key === '$') {
            // Skip attributes
            return null;
        }
        const value = tree[key];
        if (Array.isArray(value)) {
            return value.map((item, index) => (
                <div key={index} style={{paddingLeft: '1rem'}}>
                    {createElementFromTreeNode(item)}
                </div>
            ));
        } else if (typeof value === 'object') {
            return (
                <div key={key} style={{paddingLeft: '1rem'}}>
                    {createElementFromTreeNode(value)}
                </div>
            );
        } else {
            return null;
        }
    });

    // Extract message attribute if present
    const message = tree.$?.message;

    return (
        <>
            {message && <div style={{paddingLeft: '1rem'}}>{message}</div>}
            {children}
        </>
    );
}

function createElementFromTree(tree: TreeNode) {
    return <div style={{
        overflow: "scroll",
        width: "100%",
        height: "90%",
        whiteSpace: "nowrap"
    }}>
        {createElementFromTreeNode(tree)}
    </div>;
}

function App() {
    const [tree, setTree] = useState<TreeNode | undefined>(undefined);
    const [content, setContent] = useState<string[]>([]);

    useEffect(() => {
        axios({
            method: "get",
            url: ROOT + "/tree"
        }).then(response => setTree(response.data));
    }, []);

    useEffect(() => {
        axios({
            method: "get",
            url: ROOT + "/content"
        }).then(response => {
            const data1 = response.data as string;
            const lines = data1.split("\\r\\n");
            const formattedLines = lines.map((line, index) => format(index, lines.length) + " " + line);
            setContent(formattedLines);
        }).catch(error => {
            console.error("There was an error fetching the data!", error);
        });
    }, []);

    const treeElement = tree && createElementFromTree(tree);

    return (
        <>
            <div style={{
                display: "flex",
                "flexDirection": "row",
                width: "100%",
                height: "100%"
            }}>
                <div style={{
                    width: "50%"
                }}>
                    <Card label="Navigator">
                        {treeElement}
                    </Card>
                </div>
                <Card label="Viewer">
                <pre>
                    {content.map((line, index) => (
                        <div key={index}>{line}
                        </div>
                    ))}
                </pre>
                </Card>
            </div>
        </>
    )
}

export default App

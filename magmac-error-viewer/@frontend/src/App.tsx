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

function Card({label, children}: { label: string, children?: Element }) {
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

interface XMLObject {
    tagName: string;

    findAttribute(key: string): unknown | undefined;

    findChildrenByTag(tagName: string): XMLObject[];
}

function XMLObject(tagName: string, value: any): XMLObject {
    return {
        tagName,
        findAttribute(key: string): unknown | undefined {
            const $ = value.$;
            return $ ? $[key] : undefined;
        },
        findChildrenByTag(tagName: string): XMLObject[] {
            const bucket = value[tagName];
            if (!bucket || !Array.isArray(bucket)) return [];
            return bucket.map(bucketItem => XMLObject(tagName, bucketItem));
        }
    };
}

function extracted(node: XMLObject): Element {
    const parents = node.findChildrenByTag("parent").map(parent => {
        return extracted(parent);
    });

    return <>
        <div>
            {node.findAttribute("message")?.toString()}
        </div>
        <div>
            {parents}
        </div>
    </>
}

function App() {
    const [tree, setTree] = useState<XMLObject | undefined>(undefined);
    const [content, setContent] = useState<string[]>([]);
    const [selected, setSelected] = useState();

    useEffect(() => {
        axios({
            method: "get",
            url: ROOT + "/tree"
        }).then(response => setTree(XMLObject("parent", response.data.parent)));
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

    type TreeNode = {
        message: string;
        context: string;
        children: TreeNode[];
    };

    function createElementFromTree(tree: XMLObject) {
        return <div style={{
            overflow: "scroll",
            width: "100%",
            height: "70%",
            whiteSpace: "nowrap"
        }}>
            {extracted(tree)}
        </div>;
    }

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
                        {tree && createElementFromTree(tree)}
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

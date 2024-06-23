import axios from "axios";
import React, { MouseEventHandler, useEffect, useRef, useState } from "react";

function countDigits(index: number) {
    return Math.floor(index === 0 ? 0 : Math.log10(index)) + 1;
}

function format(index: number, length: number) {
    const number = countDigits(index);
    const totalLength = countDigits(length);
    return " ".repeat(totalLength - number) + index;
}

type Element = React.JSX.Element;

function Card({ label, children }: { label: string; children?: Element }) {
    return (
        <div
            style={{
                width: "100%",
                height: "100%"
            }}
        >
            <div style={{ padding: "1rem" }}>{label}</div>
            <hr />
            <div
                style={{
                    padding: "1rem",
                    height: "calc(100% - (2 * 1rem))"
                }}
            >
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
            return bucket.map((bucketItem, index) => XMLObject(tagName, bucketItem));
        }
    };
}

function TreeNode({ node, onClick }: { node: XMLObject, onClick: (object: XMLObject) => void }): Element {
    const parents = node.findChildrenByTag("parent").map((parent, index) => {
        return <TreeNode key={`parent-${index}`} node={parent} onClick={onClick} />;
    });

    const collections = node.findChildrenByTag("collection").map((collection, index) => {
        return <TreeNode key={`collection-${index}`} node={collection} onClick={onClick} />;
    });

    const children = node.findChildrenByTag("child").map((child, index) => {
        return <TreeNode key={`child-${index}`} node={child} onClick={onClick} />;
    });

    const ref = useRef<HTMLDivElement>(null);
    const handleClick: MouseEventHandler<HTMLDivElement> = (event) => {
        if (ref.current && event.target === ref.current) {
            onClick(node);
        }
    };

    return (
        <div style={{ paddingLeft: "1rem" }}>
            <span onClick={handleClick} ref={ref}>
                {node.findAttribute("message")?.toString()}
            </span>
            <div>{parents}</div>
            <div>{collections}</div>
            <div>{children}</div>
        </div>
    );
}

function App() {
    const [tree, setTree] = useState<XMLObject | undefined>(undefined);
    const [content, setContent] = useState<string[]>([]);

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

    function createElementFromTree(tree: XMLObject, onClick: (obj: XMLObject) => void) {
        return (
            <div
                style={{
                    overflow: "scroll",
                    width: "100%",
                    height: "70%",
                    whiteSpace: "nowrap"
                }}
            >
                <TreeNode node={tree} onClick={onClick} />
            </div>
        );
    }

    function onClick(obj: XMLObject) {
        console.log(obj.findAttribute("message"));
    }

    return (
        <div
            style={{
                display: "flex",
                flexDirection: "row",
                width: "100%",
                height: "100%"
            }}
        >
            <div
                style={{
                    width: "50%"
                }}
            >
                <Card label="Navigator">
                    {tree && createElementFromTree(tree, onClick)}
                </Card>
            </div>
            <Card label="Viewer">
                <pre>
                    {content.map((line, index) => (
                        <div key={index}>{line}</div>
                    ))}
                </pre>
            </Card>
        </div>
    );
}

export default App;

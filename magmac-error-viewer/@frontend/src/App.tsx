import axios from "axios";
import {createSignal, JSX, onMount} from "solid-js";

function unescape(value: string): string {
    return value.replace(/&quot;/g, "\"")
        .replace(/&lt;/g, "<")
        .replace(/&gt;/g, ">")
        .replace(/&apos;/g, "'")
        .replace(/&amp;/g, "&")
        .replace(/\\\\/g, "\\")
        .replace(/\\n/g, "\n")
        .replace(/\\t/g, "\t")
        .replace(/\\r/g, "\r");
}

interface TreeContent {
    context: string;
    title: string;
}

function TreeElement({content, children, onClick}: {
    content: TreeContent,
    children: JSX.Element,
    onClick: (content: TreeContent) => void;
}) {
    return (
        <div>
            <span onClick={() => {
                onClick(content);
            }}>
                {content.title}
            </span>
            <div style={{"padding-left": "1rem"}}>
                {children}
            </div>
        </div>
    );
}

function createTreeElement(tree: XMLObject | undefined, onClick: (content: TreeContent) => void) {
    if (!tree) return <></>;

    const parents = tree.findChildren("parent").map(parent => createTreeElement(parent, onClick));
    const collections = tree.findChildren("collection").map(collection => createTreeElement(collection, onClick));
    const children = tree.findChildren("child").map(child => createTreeElement(child, onClick));

    const message = tree.findAttribute("message") ?? "";
    const context = tree.findAttribute("context") ?? tree.findContent() ?? "";

    const content: TreeContent = {
        context: unescape(context),
        title: unescape(message)
    }

    return <TreeElement content={content} onClick={onClick}>
        {parents}
        {collections}
        {children}
    </TreeElement>
}

interface XMLObject {
    findAttribute(key: string): string | undefined;

    findChildren(key: string): XMLObject[];

    findContent(): string | undefined;
}

function XMLObject(tag: string, obj: any): XMLObject {
    return {
        findAttribute(propertyKey: string): string | undefined {
            const properties = obj.$;
            if (!properties) return undefined;
            return properties[propertyKey];
        }, findChildren(key: string): XMLObject[] {
            const children = obj[key] ?? [];
            return children.map((child: any) => XMLObject(tag, child));
        },
        findContent(): string | undefined {
            return obj["_"];
        }
    }
}

function App() {
    const [tree, setTree] = createSignal<XMLObject | undefined>(undefined);

    const [content, setContent] = createSignal("");
    const [before, setBefore] = createSignal("");
    const [highlighted, setHighlighted] = createSignal("");
    const [after, setAfter] = createSignal("");

    onMount(() => {
        axios({
            method: "get",
            url: "http://localhost:3000/tree"
        }).then(e => {
            const root = XMLObject("parent", e.data.parent);
            setTree(root);
        }).catch(e => {
            console.error(e);
        });

        axios({
            method: "get",
            url: "http://localhost:3000/content"
        }).then(e => {
            setContent(e.data);
            setBefore(e.data);
        }).catch(e => {
            console.error(e);
        });
    });

    function onClick(content: TreeContent) {
        console.log(content);
    }

    return (
        <div style={{
            display: "flex",
            "flex-direction": "row",
            "justify-content": "center",
            "align-items": "center",
            width: "100%",
            height: "100%"
        }}>
            <div style={{
                width: "80vw",
                height: "80vh"
            }}>
                <div style={{
                    display: "flex",
                    "flex-direction": "row",
                    width: "100%",
                    height: "100%"
                }}>
                    <div style={{
                        width: "100%",
                        height: "100%"
                    }}>
                        <span>
                            Navigator
                        </span>
                        {
                            createTreeElement(tree(), onClick)
                        }
                    </div>
                    <div style={{
                        width: "100%",
                        height: "100%"
                    }}>
                        <span>
                            Content
                        </span>
                        <div>
                            <span>
                                {before()}
                            </span>
                            <span style={{"background-color": "red"}}>
                                {highlighted()}
                            </span>
                            <span>
                            {after()}
                        </span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default App

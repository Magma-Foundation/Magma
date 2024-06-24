import axios from "axios";
import {createSignal, JSX, onMount} from "solid-js";

interface TreeContent {
    context: string;
    title: string;
}

function TreeElement({content, children}: { content: TreeContent, children: JSX.Element }) {
    return (
        <div>
            <span>
                {content.title}
            </span>
            <div style={{"padding-left": "1rem"}}>
                {children}
            </div>
        </div>
    );
}

function createTreeElement(tree: XMLObject | undefined) {
    if (!tree) return <></>;

    const message = tree.findAttribute("message") ?? "";

    const parents = tree.findChildren("parent").map(parent => createTreeElement(parent));
    const collections = tree.findChildren("collection").map(collection => createTreeElement(collection));
    const children = tree.findChildren("child").map(child => createTreeElement(child));

    const content: TreeContent = {
        context: "", title: message
    }

    return <TreeElement content={content}>
        {parents}
        {collections}
        {children}
    </TreeElement>
}

interface XMLObject {
    findAttribute(key: string): string | undefined;

    findChildren(key: string): XMLObject[];
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
        }
    }
}

function App() {
    const [tree, setTree] = createSignal<XMLObject | undefined>(undefined);

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
    });

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
                            createTreeElement(tree())
                        }
                    </div>
                    <div style={{
                        width: "100%",
                        height: "100%"
                    }}>
                <span>
                    Content
                </span>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default App

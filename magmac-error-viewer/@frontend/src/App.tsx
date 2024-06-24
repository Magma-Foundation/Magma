import {JSX} from "solid-js";

function TreeElement({title, children}: { title: string, children: JSX.Element }) {
    return (
        <div>
            <span>
                {title}
            </span>
            <div style={{"padding-left": "1rem"}}>
                {children}
            </div>
        </div>
    );
}

function App() {
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
                        <TreeElement title={"test"}>
                            <TreeElement title={"Child"}/>
                        </TreeElement>
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

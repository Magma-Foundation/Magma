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
                        Navigator
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

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
                padding: "1rem"
            }}>
                {children}
            </div>
        </div>
    );
}

function App() {
    const [state, setState] = useState<string[]>([]);

    useEffect(() => {
        axios({
            method: "get",
            url: "http://localhost:3000"
        }).then(response => {
            const data1 = response.data as string;
            const lines = data1.split("\\r\\n");
            const formattedLines = lines.map((line, index) => format(index, lines.length) + " " + line);
            setState(formattedLines);
        }).catch(error => {
            console.error("There was an error fetching the data!", error);
        });
    }, []);

    return (
        <>
            <div style={{
                display: "flex",
                "flexDirection": "row",
                width: "100%",
                height: "100%"
            }}>
                <div style={{
                    width: "30%"
                }}>
                    <Card label="Navigator">
                        <div>
                        </div>
                    </Card>
                </div>
                <Card label="Viewer">
                <pre>
                    {state.map((line, index) => (
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

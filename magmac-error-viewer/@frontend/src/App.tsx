import axios from "axios";
import {useEffect, useState} from "react";

function countDigits(index: number) {
    return Math.floor(index === 0 ? 0 : Math.log10(index)) + 1;
}

function format(index: number, length: number) {
    const number = countDigits(index);
    const totalLength = countDigits(length);
    return " ".repeat(totalLength - number) + index;
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
                padding: "1rem"
            }}>
                <pre>
                    {state.map((line, index) => (
                        <div key={index}>{line}
                        </div>
                    ))}
                </pre>
            </div>
        </>
    )
}

export default App

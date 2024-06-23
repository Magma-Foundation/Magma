import axios from "axios";
import {useEffect, useState} from "react";

function App() {
    const [state, setState] = useState<string[]>([]);

    useEffect(() => {
        axios({
            method: "get",
            url: "http://localhost:3000"
        }).then(response => {
            const data1 = response.data as string;
            const lines = data1
                .split("\\r\\n")
                .map((line, index) => index + " " + line);
            setState(lines);
        }).catch(error => {
            console.error("There was an error fetching the data!", error);
        });
    }, []);

    return (
        <>
            <pre>
                {state.map((line, index) => (
                    <div key={index}>{line}
                    </div>
                ))}
            </pre>
        </>
    )
}

export default App

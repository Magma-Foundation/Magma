import './App.css'
import axios from "axios";
import {useEffect, useState} from "react";

function App() {
    const [state, setState] = useState("test");

    useEffect(() => {
        axios({
            method: "get",
            url: "http://localhost:3000"
        }).then(response => {
            const data = response.data;
            console.log(data);
            setState(data);
        });
    });

    return (
        <>
            <span>
                {state}
            </span>
        </>
    )
}

export default App

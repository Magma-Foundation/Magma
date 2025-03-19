import axios from 'axios'
import './App.css'
import { createSignal, onMount } from 'solid-js'

function App() {
  const [content, setContent] = createSignal("");

  onMount(async () => {
    const response = await axios({
      method: "get",
      url: "http://localhost:3000"
    });

    setContent(response.data);
  });

  return (
    <div style={{
      "background-color": "black",
      width: "100vw",
      height: "100vh"
    }}>
      <pre style={{color: "white"}}>
        <code>
          {content()}
        </code>
      </pre>
    </div>
  )
}

export default App

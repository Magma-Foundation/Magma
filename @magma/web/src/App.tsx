import axios from 'axios'
import './App.css'
import { createSignal, onMount } from 'solid-js'
import { Text } from './Text';

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
      <div>
        <div>
          <button>
            <Text value="Save"/>
          </button>
        </div>
        <div style={{ padding: "0.5rem" }}>
          <Text value="index.mgs" />
        </div>
        <textarea style={{
          width: "100%",
          height: "100%",
          "background-color": "black",
          color: "white",
          "font-family": "monospace",
          outline: "none"
        }}>
          {content()}
        </textarea>
      </div>
    </div>
  )
}

export default App
import axios from 'axios'
import './App.css'

function App() {
  axios({
    url: "http://localhost:3000"
  })

  return (
    <div style={{
      "background-color": "black",
      width: "100vw",
      height: "100vh"
    }}>
    </div>
  )
}

export default App

import React from 'react'
import ReactDOM from 'react-dom'

import Board from './components/Board'

class App extends React.Component {
  render() {
    return (
      <Board/>
    )
  }
}

ReactDOM.render(
  <App/>,
  document.getElementById('content')
)
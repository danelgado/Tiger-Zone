import React from 'react'

import Tile from './Tile'

class Board extends React.Component {
  this.setState(tiles: [])
  
  componentWillMount() {
    for (var i = 0; i < 20; i++) {
      var tilesRow = []
      for (var j = 0; j < 20; j++) {
        tilesRow.push(
          <td>
            <Tile locX={i} locY={j} onClick={this.getNewTile}/>
          </td>
        )
      }
      
      tiles.push(
        <tr>
          {tilesRow}
        </tr>
      )  
    }
    
    this.setState({stack: tiles})
  }
  
  render() {
    return (
      <div>
        <div style={styles.panel}>
          <img src=''/>
        </div>
        <div style={styles.board}>
          <table style={styles.board}>
            <tbody>
              {tiles}
            </tbody>
          </table>
        </div>
      </div>
    )
  }
  
  getNewTile() {
    
  }
}

const styles = {
  board: {
    display: 'flex',
    zIndex: 1
  },
  panel: {
    backgroundColor: '#fff',
    borderWidth: 1,
    borderStyle: 'solid',
    borderColor: '#2099e8',
    display: 'flex',
    shadowColor: '#000',
    shadowOffset: {width: 0, height: 2},
    shadowOpacity: 0.2,
    zIndex: 2,
    position: 'fixed',
    height: '10rem',
    width: '10rem',
    alignItems: 'center',
    justifyContent: 'center'
  }
}

export default Board
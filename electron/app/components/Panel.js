import React from 'react'

class Panel extends React.Component {
  render() {
    return (
      <div style={styles.panel}>
        <img src=''/>
      </div>
    )
  }
  
  getNewTile() {
    console.log('new tile')
  }
}

const styles = {
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

export default Panel
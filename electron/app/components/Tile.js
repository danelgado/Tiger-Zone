import React from 'react'

const Tile = (props) => {
  return (
    <div onClick={props.onClick}>
      <img src=''/>
      <p style={styles.tile}>{props.locX} - {props.locY}</p>
    </div>
  )
}

const styles = {
  tile: {
    alignItems: 'center',
    backgroundColor: '#fff',
    borderWidth: 1,
    borderStyle: 'solid',
    borderColor: '#2099e8',
    display: 'flex',
    height: '10rem',
    width: '10rem',
    justifyContent: 'center'
  }
}

export default Tile
<template>
  <div id="app">
    <div id="nav"></div>
    <router-view />
  </div>
</template>
<style>
body {
  cursor: url("images/baby_Chris_head.jpg"), auto;
  background-image: url("images/baby_Chris_head.jpg") ;

}
.container {
  background-color: lightgrey;
  border: 2px solid black;
  border-radius: 10px;
  opacity: 1;
  font-family: cursive;  
  overflow-wrap: anywhere;
}

.scroll {
  overflow-y: scroll;
  word-break: break-all;
  height: 75vh;
}
h1 {
  font-size: 2em;
  line-height:100%;
}
.salmonbackground {
  background-color: salmon;
  border: 2px solid black;
  border-radius: 10px;
  opacity: 0.85;
  font-family: cursive;
  height: 100vh;
}
</style>
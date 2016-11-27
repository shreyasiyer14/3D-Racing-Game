# 3D-Racing-Game
:red_car: :taxi: 3D Racing game, using JMonkey3 engine, along with Multiplayer over LAN

##About
So, this is a small(not really) and a simplistic car racing game, me and my friends had developed as a project for our ES103-Programming-II (Java) course at International Institute of Information Technology, Bangalore. We have developed a primitive path-tracing algorithm for the single player mode, where the user matches against a bot. We have also developed a multiplayer mode, where the user can match against other players over the Local Area Network (LAN).

##Modules used
1) We used JMonkey3 game engine as the base engine for the game, utilizing it's graphics rendering engine.
<br/>
2) Bullet physics, for detection of collisions with various meshes across the world, like opponent's car mesh, bot's mesh, and the track's mesh. So, we developed a dynamic mesh collider for every Nodes in the game.
<br/>
3) SpiderMonkey Networking API for the Multiplayer feature over LAN. This is a basic message sending-and-receiving mechanism, wherein the client at each and every frame sends his transform's position and rotation to the server, and the server will then broadcast this message to other client, and that client will decrypt this message, and correspondingly move the opponent car object in it's world. Similarily, messages are sent to the server whenever the client is totally connected to it, and whether the opponent is also connected, so as to restart the match. Also, messages for completion of the laps, and the winner messages are broadcasted by the server to corresponding clients, and appropriate messages are displayed at the client's side.
<br/>

##Main highlights:
<ul>
<li>One of the important milestones was coming up with the path-tracing algorithm for the BOT. So, we just made a list of coordinates which the bot is supposed to move on, and it simply follows it. It's crude and primitive at the moment, but can be refined later.</li>
<li>Developing the server and client, along with the proper definitions of how each message is to be interpreted was one of the milestones.</li>
</ul>

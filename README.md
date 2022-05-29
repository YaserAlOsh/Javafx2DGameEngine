# Javafx2DGameEngine
A simple Game Engine written in Java that uses JavaFX for rendering and has a very simply 2D Physics Engine.

### Preview
https://user-images.githubusercontent.com/25433731/170874931-28524d7e-c643-4342-bade-aebc83e17d18.mp4

#### Problems:
Unable to run the physics simulation much faster than 30 FPS. Most likely related to the AnimationTimer class. Even at 30 FPS, there is some stuttering.
Interpolation is used to smooth the movement. With collisions however, this causes problems, especially because framerate is low.
Inelastic collisions not properly handled.

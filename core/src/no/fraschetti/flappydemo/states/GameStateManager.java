package no.fraschetti.flappydemo.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

public class GameStateManager {

    private Stack<no.fraschetti.flappydemo.states.State> states;

    public GameStateManager() {
        states = new Stack<no.fraschetti.flappydemo.states.State>();
    }

    public void push(no.fraschetti.flappydemo.states.State state) {
        states.push(state);
    }

    public void pop() {
        states.pop().dispose();
    }

    public void set(State state){
        states.pop().dispose();
        states.push(state);
    }

    public void update(float dt) {
        states.peek().update(dt);
    }

    public void render(SpriteBatch sb) {
        states.peek().render(sb);
    }
}

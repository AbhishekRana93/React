package com.mygdx.game.Listeners;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.Actors.Alien;

public class ListenerClass implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Alien dataA = (Alien) contact.getFixtureA().getBody().getUserData();
        if(dataA != null) {
            if(dataA.desc != null) {
                Gdx.app.log("Removing alien", "bla");
                dataA.remove();
                dataA.removeBody();
            }
        }
//        Gdx.app.log("Contact inititated", contact.getFixtureA().getBody().getUserData() + " :: "
//        + contact.getFixtureB().getBody().getUserData());
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}

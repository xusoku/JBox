package com.davis.gittotest.widget;

import android.view.View;

import com.davis.gittotest.R;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import java.util.Random;

/**
 * Created by xushengfu on 2017/11/23.
 */

public class JboxImp {

    private static final String TAG = "jbox";

    private World mWorld;
    private final Random mRandom=new Random();
    private float dt = 1f / 30f;

    private int mVelocityIterations = 5;//每一帧的迭代次数
    private int mPosiontionIterations = 20;

    private int mWidth, mHeight;

    private float mDesity = 0.5f;

    private float mRatio = 80f;//放大系数

    public JboxImp(float mDesity) {
        this.mDesity = mDesity;
    }

    public void setWorldSize(int mWidth, int mHeight) {
        this.mWidth = mWidth;
        this.mHeight = mHeight;
    }

    public void startWorld(){
        if(mWorld!=null){
            mWorld.step(dt,mVelocityIterations,mPosiontionIterations);
        }
    }

    public void createWorld() {
        if (mWorld == null) {
            mWorld = new World(new Vec2(0, 10.0f));
            updateVerticalBounds();
            updateHoizontalBounds();
        }
    }


    public void creatBody(View view) {

        BodyDef bodyDef = new BodyDef();

        bodyDef.type = BodyType.DYNAMIC;

//        PolygonShape box = new PolygonShape();//形状
//        float boxWidth = switchPositionToBody(mWidth);
//        float boxHeight = switchPositionToBody(mRatio);
//        box.setAsBox(boxWidth, boxHeight);

        bodyDef.position.set(switchPositionToBody(view.getX()+view.getWidth()/2),
                switchPositionToBody(view.getY()+view.getHeight()/2));



        Shape shape = null;
        Boolean isCirle = (Boolean) view.getTag(R.id.dn_view_circle_tag);

        if (isCirle != null && isCirle) {
            shape = createCircleShape(switchPositionToBody(view.getWidth() / 2));
        }
        FixtureDef fixtureDef = new FixtureDef();//摩擦系数
        fixtureDef.shape = shape;
        fixtureDef.density = mDesity;
        fixtureDef.friction = 0.8f; //摩擦系数
        fixtureDef.restitution = 0.5f;//补偿系数

        Body body=mWorld.createBody(bodyDef);
        body.createFixture(fixtureDef);
        view.setTag(R.id.dn_view_body_tag,body);

        body.setLinearVelocity(new Vec2(mRandom.nextFloat(),mRandom.nextFloat()));
    }

    private Shape createCircleShape(float v) {
        CircleShape shape = new CircleShape();
        shape.setRadius(v);
        return shape;
    }

    private void updateVerticalBounds() {
        BodyDef bodyDef = new BodyDef();

        bodyDef.type = BodyType.STATIC;

        PolygonShape box = new PolygonShape();//形状

        float boxWidth = switchPositionToBody(mWidth);
        float boxHeight = switchPositionToBody(mRatio);

        box.setAsBox(boxWidth, boxHeight);

        FixtureDef fixtureDef = new FixtureDef();//摩擦系数
        fixtureDef.shape = box;
        fixtureDef.density = mDesity;
        fixtureDef.friction = 0.8f; //摩擦系数
        fixtureDef.restitution = 0.5f;//补偿系数

        bodyDef.position.set(0, -boxHeight);      //底部弹力墙
        Body bottomBody = mWorld.createBody(bodyDef);
        bottomBody.createFixture(fixtureDef);

        bodyDef.position.set(0, switchPositionToBody(mHeight) + boxHeight); //钉部弹力墙
        Body topBody = mWorld.createBody(bodyDef);
        topBody.createFixture(fixtureDef);


    }

    private void updateHoizontalBounds() {
        BodyDef bodyDef = new BodyDef();

        bodyDef.type = BodyType.STATIC;

        PolygonShape box = new PolygonShape();//形状

        float boxWidth = switchPositionToBody(mRatio);
        float boxHeight = switchPositionToBody(mHeight);

        box.setAsBox(boxWidth, boxHeight);

        FixtureDef fixtureDef = new FixtureDef();//摩擦系数
        fixtureDef.shape = box;
        fixtureDef.density = mDesity;
        fixtureDef.friction = 0.8f; //摩擦系数
        fixtureDef.restitution = 0.5f;//补偿系数

        bodyDef.position.set(-boxWidth, 0);      //底部弹力墙
        Body leftBody = mWorld.createBody(bodyDef);
        leftBody.createFixture(fixtureDef);

        bodyDef.position.set(switchPositionToBody(mWidth) + boxWidth, 0); //钉部弹力墙
        Body rightBody = mWorld.createBody(bodyDef);
        rightBody.createFixture(fixtureDef);

    }

    public boolean isBodyView(View view) {
        Body body = (Body) view.getTag(R.id.dn_view_body_tag);
        return body != null;
    }

    private float switchPositionToBody(float viewPosition) {
        return viewPosition / mRatio;
    }

    private float switchPositionToView(float bodyPosition) {
        return bodyPosition * mRatio;
    }


    public float getViewX(View view) {
        Body body = (Body) view.getTag(R.id.dn_view_body_tag);
        if (body != null) {
            return switchPositionToView(body.getPosition().x) - view.getWidth() / 2;
        }
        return 0;
    }

    public float getViewY(View view) {
        Body body = (Body) view.getTag(R.id.dn_view_body_tag);
        if (body != null) {
            return switchPositionToView(body.getPosition().y) - view.getHeight() / 2;
        }
        return 0;
    }

    public float getViewRotaion(View view) {
        Body body = (Body) view.getTag(R.id.dn_view_body_tag);
        if (body != null) {
            float angle = body.getAngle();
            return (angle / 3.14f * 180f) % 360;
        }
        return 0;
    }

    public void applyLinearImp(float x,float y,View view){
        Body boyd= (Body) view.getTag(R.id.dn_view_body_tag);
        Vec2 im=new Vec2(x,y);
        boyd.applyLinearImpulse(im,boyd.getPosition(),true);
    }


}

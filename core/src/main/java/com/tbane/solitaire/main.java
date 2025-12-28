package com.tbane.solitaire;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.utils.ScreenUtils;

import com.badlogic.gdx.utils.viewport.FitViewport;
import com.tbane.solitaire.GUI.Font;
import com.tbane.solitaire.MyInput.MyInputProcessor;
import com.tbane.solitaire.MyInput.MyInput;
import com.tbane.solitaire.Views.LayoutsManager;
import com.tbane.solitaire.GUI.GUI;
import com.tbane.solitaire.Views.MainMenu;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class main extends ApplicationAdapter {

    private float FPStimer;
    private float FPScount;
    private String FPStext;

    public Environment environment;
    public Model model;
    public ModelInstance instance;

    @Override
    public void create() {

        Renderer.camera2D = new OrthographicCamera();

        Renderer.camera3D = new PerspectiveCamera(67, Renderer.VIRTUAL_WIDTH, Renderer.VIRTUAL_HEIGHT);
        Renderer.camera3D.position.set(15, 15, 50);
        Renderer.camera3D.lookAt(0, 0, 0);
        Renderer.camera3D.near = 0.1f;
        Renderer.camera3D.far = 1000f;
        Renderer.camera3D.update();

        // send the events to MyInput
        Gdx.input.setCatchKey(Input.Keys.BACK, true);

        // set the input processor
        MyInput.processor = new MyInputProcessor(Renderer.camera2D);
        Gdx.input.setInputProcessor(MyInput.processor);

        // create main Layout - MainMenu
        LayoutsManager.array.add(new MainMenu());

        FPStimer = Time.currentTime;
        FPScount = 0;
        FPStext = "0";

        ModelBuilder modelBuilder = new ModelBuilder();
        model = modelBuilder.createBox(5f, 5f, 5f,
            new Material(ColorAttribute.createDiffuse(Color.GREEN)),
            VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        instance = new ModelInstance(model);

        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.8f, 0.8f, 0.8f, 1f));
        environment.add(new DirectionalLight().set(Color.WHITE, -1f, -0.8f, -0.2f));

    }

    @Override
    public void render() {

        Time.update();

        if(Time.currentTime - FPStimer > 1){
            FPStimer = Time.currentTime;
            FPStext = Float.toString(FPScount);
            FPScount = 0;
        }

        FPScount += 1;

        if(MyInput.processor.isTouchDown() || MyInput.processor.isTouchUp() || MyInput.processor.isTouchMoved() || MyInput.processor.isBackPressed()){
            GUI.ElementGUI_hovered = null;
            LayoutsManager.back().handleEvents();
            // reset the Events
            MyInput.processor.reset();
        }



        LayoutsManager.back().update();

        // OpenGL stuff - start
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);

        // render 2D
        ScreenUtils.clear(0, 0, 0, 1);
        Renderer.begin2D(); // ortogrtaphics camera
        LayoutsManager.back().draw();
        Font.descriptionFont.draw(Renderer.spriteBatch, FPStext, 16, 16+Font.descriptionFont.getCapHeight());
        Renderer.end2D();

        // render 3D model
        Renderer.begin3D(); // prespective camera
        Renderer.modelBatch.render(instance, environment);
        Renderer.end3D();

        // OpenGL stuff - end
        Gdx.gl.glDisable(GL20.GL_DEPTH_TEST);
    }

    @Override
    public void dispose() {

    }
}

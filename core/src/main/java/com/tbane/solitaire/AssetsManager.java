package com.tbane.solitaire;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.g3d.utils.TextureProvider;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;

public class AssetsManager {
    public static final AssetManager manager = new AssetManager();

    static {
        manager.setLoader(Model.class, ".obj", new ObjLoader(new InternalFileHandleResolver()));
    }

    // --- Tekstury (jak wcześniej) ---
    public static void loadTexture(String path) {
        if (!manager.isLoaded(path, Texture.class)) {
            manager.load(path, Texture.class);
        }
    }

    public static boolean update() {
        return manager.update(); // zwraca true, gdy wszystko załadowane
    }

    public static float getProgress() {
        return manager.getProgress(); // 0..1
    }

    public static Texture getTexture(String path) {
        if (manager.isLoaded(path, Texture.class)) {
            return manager.get(path, Texture.class);
        }
        return null;
    }

    public static void unloadTexture(String path) {
        if (manager.isLoaded(path, Texture.class)) {
            manager.unload(path);
        }
    }

    // --- Modele ---
    public static void loadModel(String path) {
        if (!manager.isLoaded(path, Model.class)) {
            manager.load(path, Model.class);
        }
    }

    public static Model getModel(String path) {
        if (manager.isLoaded(path, Model.class)) {
            return manager.get(path, Model.class);
        }
        return null;
    }

    public static void unloadModel(String path) {
        if (manager.isLoaded(path, Model.class)) {
            manager.unload(path);
        }
    }

    public static void dispose() {
        manager.dispose();
    }
}

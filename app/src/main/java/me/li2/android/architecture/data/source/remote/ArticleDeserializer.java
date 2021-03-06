package me.li2.android.architecture.data.source.remote;

import android.text.TextUtils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import javax.inject.Inject;

import me.li2.android.architecture.data.model.Article;

/**
 * Created by weiyi on 18/2/18.
 * https://github.com/li2
 */

public class ArticleDeserializer implements JsonDeserializer {

    @Inject
    public ArticleDeserializer() {
    }

    @Override
    public Article deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String title = null, description = null, imageHref = null;

        final JsonObject jsonObject = json.getAsJsonObject();
        JsonElement element;

        if (!jsonObject.isJsonNull()) {
            element = jsonObject.get("title");
            if (!element.isJsonNull()) {
                title = element.getAsString();
            }

            element = jsonObject.get("description");
            if (!element.isJsonNull()) {
                description = element.getAsString();
            }

            element = jsonObject.get("imageHref");
            if (!element.isJsonNull()) {
                imageHref = element.getAsString();
                if (TextUtils.isEmpty(imageHref)) {
                    imageHref = null; // Picasso.load path must not be empty.
                }
            }
        }

        if (TextUtils.isEmpty(title) && TextUtils.isEmpty(description) && TextUtils.isEmpty(imageHref)) {
            return null;
        }

        return new Article(title, description, imageHref);
    }
}

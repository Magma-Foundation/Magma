package com.meti.app.compile.node;

import com.meti.api.json.JSONException;
import com.meti.api.json.JSONNode;

public interface JSONable {
    JSONNode toJSON() throws JSONException;
}

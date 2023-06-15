package dev.nightfeather.basic_build.widget

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.mutablePreferencesOf
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.glance.*
import androidx.glance.action.Action
import androidx.glance.action.ActionParameters
import androidx.glance.action.action
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.glance.appwidget.lazy.itemsIndexed
import androidx.glance.appwidget.provideContent
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.layout.*
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import dev.nightfeather.basic_build.Constants
import dev.nightfeather.basic_build.R
import kotlinx.coroutines.delay
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

object NewsWidget: GlanceAppWidget() {
    val newsJsonDataKey = stringPreferencesKey("newsJsonData")

    private fun parseList(json: JSONObject): ArrayList<JSONObject> {
        val a = arrayListOf<JSONObject>()
        json.keys().forEach {
            a.add(json[it] as JSONObject)
        }
        return a
    }

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            val newsJsonData = JSONObject(currentState(key = newsJsonDataKey) ?: "{}")
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = GlanceModifier
                    .fillMaxSize()
                    .background(ImageProvider(R.drawable.widget_rounded_bg_gray))
            ) {
                Button(
                    text = "刷新",
                    onClick = actionRunCallback(RefreshNewsAction::class.java),
                )

                LazyColumn(
                    modifier = GlanceModifier
                        .padding(horizontal = 4.dp)
                ) {
                    itemsIndexed(parseList(newsJsonData)) {idx, data ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = GlanceModifier
                                .fillMaxSize()
                                .padding(4.dp)
                                .padding(horizontal = 8.dp)
                                .background(ImageProvider(R.drawable.widget_rounded_bg))
                        ) {
                            Text(
                                text = "${idx + 1}.",
                                style = TextStyle(
                                    fontSize = 20.sp
                                )
                            )
                            Column(
                                modifier = GlanceModifier
                                    .padding(horizontal = 12.dp)
                            ) {
                                Text(text = data["title"] as String)
                                Text(text = data["content"] as String)
                            }
                        }
                    }
                }
            }
        }
    }
}

class NewsWidgetReceiver: GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget
        get() = NewsWidget
}

object RefreshNewsAction: ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        val httpClient = OkHttpClient()
        val req = Request.Builder()
            .get()
            .url("${Constants.baseUrl}/guide/news/posts")
            .build()
        val res = httpClient.newCall(req).execute()
        updateAppWidgetState(context, glanceId) { prefs ->
            prefs[NewsWidget.newsJsonDataKey] = res.body!!.string()
        }
        NewsWidget.update(context, glanceId)
    }
}
/*
 * Copyright 2021 Stream.IO, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.stream.avengerschat.components

import android.view.View
import androidx.lifecycle.LifecycleOwner
import io.getstream.chat.android.ui.channel.list.ChannelListView
import io.getstream.chat.android.ui.channel.list.viewmodel.ChannelListViewModel
import io.getstream.chat.android.ui.channel.list.viewmodel.bindView
import io.getstream.chat.android.ui.channel.list.viewmodel.factory.ChannelListViewModelFactory
import io.stream.avengerschat.R

/**
 * Stream channel list UI component.
 *
 * [Stream Channel List](https://getstream.io/chat/docs/sdk/android/ui/components/channel-list-screen/)
 */
class StreamChannelListUIComponent constructor(
    override val lifecycleOwner: LifecycleOwner
) : StreamUIComponent {
    private val threadSafetyMode: LazyThreadSafetyMode = LazyThreadSafetyMode.NONE

    private val factory by lazy(threadSafetyMode) {
        ChannelListViewModelFactory()
    }

    private val channelListViewModel: ChannelListViewModel by lazy(threadSafetyMode) {
        factory.create(ChannelListViewModel::class.java)
    }

    @StreamComponentHighlighter
    override fun bindLayout(view: View) {
        val channelListView =
            view.findViewById<ChannelListView>(R.id.channelListView)
        channelListView?.let {
            channelListViewModel.bindView(it, lifecycleOwner)
        }
    }
}

@StreamComponentHighlighter
fun LifecycleOwner.streamChannelListComponent(): Lazy<StreamChannelListUIComponent> {
    return lazy(LazyThreadSafetyMode.NONE) {
        StreamChannelListUIComponent(lifecycleOwner = this)
    }
}

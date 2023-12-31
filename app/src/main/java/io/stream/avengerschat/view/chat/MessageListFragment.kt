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

package io.stream.avengerschat.view.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.getstream.sdk.chat.viewmodel.messages.MessageListViewModel
import com.skydoves.bindables.BindingFragment
import dagger.hilt.android.AndroidEntryPoint
import io.stream.avengerschat.R
import io.stream.avengerschat.components.StreamMessageListUIComponent
import io.stream.avengerschat.components.streamMessageListComponent
import io.stream.avengerschat.databinding.FragmentMessageListBinding
import io.stream.avengerschat.extensions.addOnBackPressedDispatcher
import io.stream.avengerschat.view.home.HomeViewModel

@AndroidEntryPoint
class MessageListFragment :
    BindingFragment<FragmentMessageListBinding>(R.layout.fragment_message_list) {

    private val args: MessageListFragmentArgs by navArgs()
    private val homeViewModel: HomeViewModel by activityViewModels()
    private val streamMessageListComponent: StreamMessageListUIComponent by streamMessageListComponent()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding {
            vm = homeViewModel
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addOnBackPressedDispatcher { backHandler.invoke() }

        binding {
            // initializes and bind layouts to Stream message list UI components.
            streamMessageListComponent.initIds(args.cid)
            streamMessageListComponent.bindLayout(root)
            messageListHeaderView.setBackButtonClickListener(backHandler)
        }
    }

    override fun onResume() {
        super.onResume()
        homeViewModel.visibleBottomNav = false
    }

    override fun onPause() {
        super.onPause()
        homeViewModel.visibleBottomNav = true
    }

    private val backHandler = {
        findNavController().popBackStack()
        streamMessageListComponent.messageListViewModel.onEvent(MessageListViewModel.Event.BackButtonPressed)
    }
}

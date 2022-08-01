/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package client;

import client.scenes.*;
import client.utils.QuestionUtils;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Scopes;


public class MyModule implements Module {

    @Override
    public void configure(Binder binder) {
        binder.bind(AddActivityController.class).in(Scopes.SINGLETON);
        binder.bind(AdminPanelController.class).in(Scopes.SINGLETON);
        binder.bind(CloseConfirmationController.class).in(Scopes.SINGLETON);
        binder.bind(GameIDInputController.class).in(Scopes.SINGLETON);
        binder.bind(GamemodeSelectionController.class).in(Scopes.SINGLETON);
        binder.bind(MainCtrl.class).in(Scopes.SINGLETON);
        binder.bind(MainMenuController.class).in(Scopes.SINGLETON);
        binder.bind(MatchHistoryController.class).in(Scopes.SINGLETON);
        binder.bind(MultiplayerEndLeaderboardController.class).in(Scopes.SINGLETON);
        binder.bind(NameInputController.class).in(Scopes.SINGLETON);
        binder.bind(NameInputMatchHistoryController.class).in(Scopes.SINGLETON);
        binder.bind(ClosedQuestionController.class).in(Scopes.SINGLETON);
        binder.bind(OpenQuestionController.class).in(Scopes.SINGLETON);
        binder.bind(SettingsController.class).in(Scopes.SINGLETON);
        binder.bind(SingleplayerEndLeaderboardController.class).in(Scopes.SINGLETON);
        binder.bind(Tutorial1Controller.class).in(Scopes.SINGLETON);
        binder.bind(Tutorial2Controller.class).in(Scopes.SINGLETON);
        binder.bind(Tutorial3Controller.class).in(Scopes.SINGLETON);
        binder.bind(Tutorial4Controller.class).in(Scopes.SINGLETON);
        binder.bind(WaitingRoomController.class).in(Scopes.SINGLETON);
        binder.bind(Player.class).in(Scopes.SINGLETON);
        binder.bind(QuestionUtils.class).in(Scopes.SINGLETON);
    }
}
package com.flexnet.presentation.feature.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.flexnet.presentation.feature.list.component.RuleItem

@Composable
internal fun NetworkRulesScreen(state: NetworkRulesState, event: (NetworkRulesEvent) -> Unit) {
    Scaffold(
        floatingActionButton = {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(50.dp)
                    .clickable {
                        event(NetworkRulesEvent.NavigateToAddRule())
                    },
            ) {
                Image(
                    imageVector = Icons.Filled.AddCircle,
                    contentDescription = "add",
                    modifier = Modifier.size(50.dp),
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End,
    ) { it ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
        ) {
            items(state.networkRules) { rule ->
                RuleItem(
                    networkRule = rule,
                    lastItem = state.networkRules.last() == rule,
                    onRuleClick = {
                        event(NetworkRulesEvent.NavigateToAddRule(it))
                    },
                    onToggleChange = { isActive, networkRule ->
                        event(NetworkRulesEvent.NetworkRuleToggle(isActive, networkRule))
                    },
                )
            }
        }
    }
}

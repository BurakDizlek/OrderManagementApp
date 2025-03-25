package com.bd.ordermanagementapp.screens.home.campaign

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.bd.ordermanagementapp.screens.main.GraphRoute
import kotlinx.serialization.json.Json
import java.net.URLDecoder
import java.net.URLEncoder


fun NavGraphBuilder.campaignDetailsNavigationGraph(navController: NavHostController) {
    navigation(
        startDestination = "campaign_details/{campaignData}",
        route = GraphRoute.CAMPAIGN_DETAILS
    ) {
        composable("campaign_details/{campaignData}") { backStackEntry ->
            val campaignDataString = backStackEntry.arguments?.getString("campaignData")
            val campaignData = campaignDataString?.let {
                try {
                    val decodedData = URLDecoder.decode(it, "UTF-8")
                    Json.decodeFromString(CampaignDetailsScreenData.serializer(), decodedData)
                } catch (e: Exception) {
                    null
                }
            }
            CampaignDetailsScreen(data = campaignData, navController = navController)
        }
    }
}

fun NavHostController.navigateToCampaignDetails(
    data: CampaignDetailsScreenData
) {
    val encodedData = URLEncoder.encode(Json.encodeToString(data), "UTF-8")
    navigate("campaign_details/$encodedData")
}
package com.example.seeme.ui.navigation

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.seeme.ui.auth.AuthDialog
import com.example.seeme.ui.auth.AuthViewModel
import com.example.seeme.ui.broadcast.BroadcastScreen
import com.example.seeme.ui.coin.CoinViewModel
import com.example.seeme.ui.components.TokenPurchaseDialog
import com.example.seeme.ui.discover.CreatorSuggestionsScreen
import com.example.seeme.ui.discover.DiscoverHomeScreen
import com.example.seeme.ui.feed.FeedScreen
import com.example.seeme.ui.profile.EditProfileScreen
import com.example.seeme.ui.profile.ProfileScreen
import com.example.seeme.ui.splash.SplashScreen
import com.example.seeme.ui.theme.*
import com.example.seeme.ui.wallet.WalletScreen

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Home : Screen("home")
    object Feed : Screen("feed")
    object Broadcast : Screen("broadcast")
    object Profile : Screen("profile")
    object Wallet : Screen("wallet")
    object EditProfile : Screen("edit_profile")
    object CreatorSuggestions : Screen("creator_suggestions")
}

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    coinViewModel: CoinViewModel = viewModel(),
    authViewModel: AuthViewModel = viewModel()
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val wallet by coinViewModel.wallet.collectAsState()
    val transactions by coinViewModel.transactions.collectAsState()
    val currentUser by authViewModel.currentUser.collectAsState()

    var showTokenPurchaseDialog by remember { mutableStateOf(false) }
    var showAuthDialog by remember { mutableStateOf(false) }

    val showBottomBar = currentRoute in listOf(Screen.Home.route, Screen.Profile.route)

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar(
                    containerColor = SmokeWhiteSurface,
                    contentColor = InstagramTextDark,
                    tonalElevation = 8.dp,
                    modifier = Modifier
                        .height(72.dp)
                        .border(1.dp, SmokeWhiteBorder)
                ) {
                    NavigationBarItem(
                        selected = currentRoute == Screen.Home.route,
                        onClick = {
                            navController.navigate(Screen.Home.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = if (currentRoute == Screen.Home.route) Icons.Filled.Home else Icons.Outlined.Home,
                                contentDescription = "Home"
                            )
                        },
                        label = { Text("Discover", color = if (currentRoute == Screen.Home.route) InstagramPink else InstagramTextSecondary) },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = InstagramPink.copy(alpha = 0.12f),
                            selectedIconColor = InstagramPink,
                            unselectedIconColor = InstagramTextSecondary
                        )
                    )

                    // Go Live Studio FAB (Requires Auth)
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        contentAlignment = Alignment.Center
                    ) {
                        FloatingActionButton(
                            onClick = {
                                if (currentUser.isGuest) {
                                    showAuthDialog = true
                                } else {
                                    navController.navigate(Screen.Broadcast.route)
                                }
                            },
                            containerColor = InstagramPink,
                            contentColor = Color.White,
                            shape = CircleShape,
                            modifier = Modifier
                                .size(52.dp)
                                .shadow(6.dp, CircleShape)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Videocam,
                                contentDescription = "Go Live",
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    }

                    NavigationBarItem(
                        selected = currentRoute == Screen.Profile.route,
                        onClick = {
                            navController.navigate(Screen.Profile.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = if (currentRoute == Screen.Profile.route) Icons.Filled.Person else Icons.Outlined.Person,
                                contentDescription = "Profile"
                            )
                        },
                        label = { Text("Profile", color = if (currentRoute == Screen.Profile.route) InstagramPink else InstagramTextSecondary) },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = InstagramPink.copy(alpha = 0.12f),
                            selectedIconColor = InstagramPink,
                            unselectedIconColor = InstagramTextSecondary
                        )
                    )
                }
            }
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavHost(
                navController = navController,
                startDestination = Screen.Splash.route,
                modifier = Modifier.fillMaxSize()
            ) {
                composable(Screen.Splash.route) {
                    SplashScreen(
                        onFinishOnboarding = {
                            navController.navigate(Screen.Home.route) {
                                popUpTo(Screen.Splash.route) { inclusive = true }
                            }
                        }
                    )
                }
                composable(Screen.Home.route) {
                    DiscoverHomeScreen(
                        onSelectCreatorStream = { _ ->
                            navController.navigate(Screen.Feed.route)
                        },
                        onOpenSuggestions = {
                            navController.navigate(Screen.CreatorSuggestions.route)
                        }
                    )
                }
                composable(Screen.CreatorSuggestions.route) {
                    CreatorSuggestionsScreen(
                        onBack = { navController.popBackStack() },
                        onSelectCreator = { _ ->
                            navController.navigate(Screen.Feed.route)
                        }
                    )
                }
                composable(Screen.Feed.route) {
                    FeedScreen(coinViewModel = coinViewModel)
                }
                composable(Screen.Broadcast.route) {
                    BroadcastScreen(
                        onClose = { navController.popBackStack() }
                    )
                }
                composable(Screen.Profile.route) {
                    ProfileScreen(
                        userProfile = currentUser,
                        wallet = wallet,
                        onRequireAuth = { showAuthDialog = true },
                        onEditProfile = { navController.navigate(Screen.EditProfile.route) },
                        onOpenWallet = { navController.navigate(Screen.Wallet.route) },
                        onTopUpCoins = { showTokenPurchaseDialog = true }
                    )
                }
                composable(Screen.Wallet.route) {
                    WalletScreen(
                        wallet = wallet,
                        transactions = transactions,
                        onBack = { navController.popBackStack() },
                        onTopUp = { showTokenPurchaseDialog = true }
                    )
                }
                composable(Screen.EditProfile.route) {
                    EditProfileScreen(
                        onBack = { navController.popBackStack() },
                        onSave = { navController.popBackStack() }
                    )
                }
            }

            // Mandatory Go Live Creator Sign Up Gate Sheet
            if (showAuthDialog) {
                AuthDialog(
                    onDismiss = { showAuthDialog = false },
                    onSignUpCreator = { name, handle, email, password, country, category ->
                        authViewModel.signUpCreator(name, handle, email, country, category)
                        showAuthDialog = false
                        navController.navigate(Screen.Broadcast.route)
                    },
                    onSignIn = { email ->
                        authViewModel.signIn(email)
                        showAuthDialog = false
                        navController.navigate(Screen.Broadcast.route)
                    }
                )
            }

            if (showTokenPurchaseDialog) {
                TokenPurchaseDialog(
                    currencies = coinViewModel.supportedCurrencies,
                    paymentGateways = coinViewModel.paymentGateways,
                    tokenPackages = coinViewModel.tokenPackages,
                    onDismiss = { showTokenPurchaseDialog = false },
                    onConfirmPurchase = { pkg, gateway, currency ->
                        coinViewModel.processTokenPurchase(pkg, gateway, currency)
                        showTokenPurchaseDialog = false
                    }
                )
            }
        }
    }
}

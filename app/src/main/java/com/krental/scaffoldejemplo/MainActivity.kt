package com.krental.scaffoldejemplo

import android.R.attr.enabled
import android.R.attr.onClick
import android.R.attr.text
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.FloatingActionButtonElevation
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.krental.scaffoldejemplo.ui.theme.ScaffoldEjemploTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ScaffoldEjemploTheme {
                ScaffoldScrollTabRow()
                //ScaffoldTabRow()
                //ScaffoldCenterAlignedTopAppBar()
            }
        }
    }
}

@Composable
fun ScaffoldScrollTabRow(){
    val tabTitles = listOf("Incio","Favoritos","Perfil","Configuracion","Notificaciones")

    var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            ScrollableTabRow(
                selectedTabIndex = selectedTabIndex,
                modifier = Modifier.statusBarsPadding(),
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                edgePadding = 12.dp,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        Modifier
                            .tabIndicatorOffset(tabPositions[selectedTabIndex]),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            ) {
                tabTitles.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = {
                            selectedTabIndex = index
                        },
                        text = {
                            Text(
                                text = title,
                                fontSize = 16.sp,
                                style = MaterialTheme.typography.labelLarge
                            )
                        }
                    )
                }
            }
        }
    ){ paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ){
            Text(
                text = "Pestaña: ${tabTitles[selectedTabIndex]}",
                style = MaterialTheme.typography.headlineSmall,
                fontSize = 16.sp
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldTabRow() {
    val tabTitles = listOf("Inicio","Favoritos","Perfil")
    //Estas son las pestañas. Se recomienda de 3 a 5. Con
    //mas pestañas es preferible un ScrollTabRow

    val tabIcons = listOf(
        Icons.Default.Home,
        Icons.Default.Favorite,
        Icons.Default.Person
    )

    var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }

    val interactionSources = remember {
        List(tabTitles.size) {
            MutableInteractionSource()
        }
    }
    //Lista de fuentes de interaccion (interation source)
    //Sensor invisible que se le asigna a un componente boton, tarjeta
    //o pestaña. Permitira detectar toques, presion prolongada o un
    //enfoque desde el teclado. Se usa para efectos visuales,
    //animaciones o detectar eventos de interaccion

    Scaffold(
        topBar = {
            TabRow(
                selectedTabIndex = selectedTabIndex,
                modifier = Modifier.statusBarsPadding(),
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                indicator = { tabPosition ->
                    TabRowDefaults.SecondaryIndicator(
                        modifier = Modifier
                            .tabIndicatorOffset(tabPosition[selectedTabIndex])
                            .height(3.dp),
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                divider = {
                    HorizontalDivider(
                        color = MaterialTheme.colorScheme.outlineVariant,
                        thickness = 1.dp
                    )
                }
            ){
                tabTitles.forEachIndexed { index, title ->

                    val enabled = index != 1

                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = {
                            //selectedTabIndex = index
                            if (enabled) selectedTabIndex = index
                        },
                        modifier = Modifier.padding(horizontal = 4.dp),
                        enabled = enabled,
                        text = {
                            Text(
                                text = title,
                                fontSize = 16.sp,
                                style = MaterialTheme.typography.titleLarge
                            )
                        },
                        icon = {
                            Icon(
                                imageVector = tabIcons[index],
                                contentDescription = title
                            )
                        },
                        selectedContentColor = MaterialTheme.colorScheme.onPrimary,
                        unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        interactionSource = interactionSources[index]
                    )
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ){
            when(selectedTabIndex){
                0 -> Text("Pantalla de Inicio" , style= MaterialTheme.typography.bodyLarge)
                1 -> Text("Pantalla de Favoritos" , style= MaterialTheme.typography.bodyLarge)
                2 -> Text("Pantalla de Perfil" , style= MaterialTheme.typography.bodyLarge)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldCenterAlignedTopAppBar() {
    val items = listOf("Inicio","Buscar","Perfil")
    val icons = listOf(Icons.Default.Home,Icons.Default.Search,Icons.Default.Person)
    var selectedItem by rememberSaveable { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text="Mi aplicacion")
                },
                navigationIcon = {
                    IconButton(
                        onClick = { }
                    ){
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menu"
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { /* Accion */ },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = "Notificaciones"
                        )
                    }

                    IconButton(
                        onClick = { /* Accion */ },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Configuración"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        bottomBar = {
            NavigationBar(
                modifier = Modifier.fillMaxWidth(),
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                tonalElevation = 3.dp,
                windowInsets = NavigationBarDefaults.windowInsets
            ){
                items.forEachIndexed { index, label ->
                    NavigationBarItem(
                        onClick = { selectedItem = index },
                        selected = selectedItem == index,
                        icon = {
                            Icon(
                                imageVector = icons[index],
                                contentDescription = label
                            )
                        },
                        label = {
                            Text(text = label)
                        },
                        alwaysShowLabel = true
                    )
                }
            }
            //No podemos utilizar un ButtonAppBar y un NavigationBar
            //Solamente podemos implementar uno de ellos
            /*
            BottomAppBar(
                actions = {
                    IconButton(onClick={/* Accion de inicio */}){
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "Inicio"
                        )
                    }

                    IconButton(onClick={/* Accion */}){
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Buscar"
                        )
                    }

                    IconButton(onClick={/* Accion */}){
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Favoritos"
                        )
                    }
                },
                tonalElevation = 6.dp,
                windowInsets = WindowInsets.navigationBars,
                //BottomAppBar se muestre correctamente sobre la barra de navegacion
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface
            )
             */
        },
        floatingActionButton = {
            FloatingActionButton( //Small - Large
                onClick = { /* Accion click en el boton */ },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                shape = CircleShape,
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = 6.dp, //Sombra en reposo
                    pressedElevation = 12.dp //Sombra cuando presiona
                )
            ){
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Agregar"
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        content = { paddingValues ->
            //Contenido principal
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Column() {
                    Text(text = "Contenido")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Pantalla ${items[selectedItem]}")
                }
            }
        }
    )
}
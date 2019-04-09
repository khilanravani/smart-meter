# Android Application

An android application for the users of smart meter.

The app shows the current reading of the user's meter, user's profile details, Bills  and History and user's statistics. 

## Working
App fetches the data from the deployed REST API and renders it on the UI.

## Implementation Details

* **[Volley_Library](https://developer.android.com/training/volley)** is used for sending and receiving the data from the API.
* **[GraphView](http://www.android-graphview.org/)** is used for plotting the graph.
* **[RecyclerView](https://developer.android.com/guide/topics/ui/layout/recyclerview)** is used to render the card list on the Billings and History fragmet.

## Screenshots

<table>
    <tr>
        <td><img src = "../imgs/screenshots/app_login.png" alt="Login Page"></td>
        <td><img src="../imgs/screenshots/app_home.png" alt="Home Page"></td>
        <td><img src="../imgs/screenshots/app_drawer.png" alt="Navigation Drawer"></td>
    </tr>
    <tr>
        <td><img src = "../imgs/screenshots/app_billing.png" alt="Bills and History"></td>
        <td><img src="../imgs/screenshots/app_graph.png" alt="Statistics Page"></td>
        <td><img src="../imgs/screenshots/app_user_profile.png" alt="User Profile"></td>
    </tr>
</table>

## Contributing

Found a bug? Create an **[issue](https://github.com/Hsankesara/smart-meter/issues/new)**.
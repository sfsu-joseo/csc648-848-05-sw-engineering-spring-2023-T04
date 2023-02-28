import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import './App.css';
import Home from './components/pages/Home';
import Saru from './components/pages/Saru';
import Elias from './components/pages/Elias';
import Josh from './components/pages/Josh';
import Kilian from './components/pages/Kilian';
import Omar from './components/pages/Omar';
import Parth from './components/pages/Parth';
import Wilson from './components/pages/Wilson';

function App() {
    return (
        <>
         <Router>
            <Routes>
            <Route exact path="/" element={<Home/>}> </Route>
            <Route path='/saru' element={<Saru />}></Route>
            <Route path='/elias' element={<Elias />}></Route>
            <Route path='/josh' element={<Josh />}></Route>
            <Route path='/kilian' element={<Kilian />}></Route>
            <Route path='/omar' element={<Omar />}></Route>
            <Route path='/parth' element={<Parth />}></Route>
            <Route path='/wilson' element={<Wilson />}></Route>
            </Routes>
        </Router> 
        </>
    );
}

export default App;
import user from '../src/img/user.png'
import notification from '../src/img/notification.png'
import edit from '../src/img/edit.png';
import inbox from '../src/img/envelope.png';
import settings from '../src/img/settings.png';
import help from '../src/img/question.png';
import logout from '../src/img/log-out.png';

//import avatar-angela-gray from '../src/images/avatar-angela-gray.webp'
import Navbar from './Navbar';

import { useState, useEffect, useRef} from 'react';
function App() {
  const [ open,setOpen ] = useState('user');
  
  const [ show, setShow ] = useState('notifications');

  //const Notification =() => {
    //let name = "hello";

   // const handleClick = () => {
   //   let name = "new";
 //   }
 // }


  let menuRef = useRef();

  useEffect (() => {
    let handler = (e) => { 
      if(!menuRef.current.contains(e.target)) {
        setOpen(false);
        console.log(menuRef.current);
      }      
    };

  //useEffect (() => {
   //   setShow(() => );
   // })
    

    document.addEventListener("mousedown", handler);
    

    return() =>{
      document.removeEventListener("mousedown", handler);
    }

  });

  

  return (
    <div className="App">
      <div>  
       {/* <div className='menu-container' ref={menuRef}> 
        <div className='menu-trigger' onClick={()=>{setShow(!show)}}></div>
       
          <button onClick={alert('hello')}>
            
          </button>
  </div> */}
      
   </div>
       <div className='menu-container' ref={menuRef}> 
        <div className='menu-trigger' onClick={()=>{setOpen(!open)}}>
          <img src={user}></img>
        </div>
       
          <div className = 'container'>
          <button type ='button'onClick={(alert) => setShow}>click me</button>
          
          
          <img src={notification}></img>
          </div>
        
    </div>  
    
      <div className={`dropdown-menu ${open? 'active' : 'inactive'}`} >
          <h3>User account<br/><span>Student</span></h3>
          
          <ul>
            <DropdownItem img = {user} text = {"My Profile"}/>
            <DropdownItem img = {edit} text = {"Edit Profile"}/>
            <DropdownItem img = {inbox} text = {"Inbox"}/>
            <DropdownItem img = {settings} text = {"Settings"}/>
            <DropdownItem img = {help} text = {"Helps"}/>
            <DropdownItem img = {logout} text = {"Logout"}/>
          </ul>
          
        </div>
      </div>
  );
  
}

function DropdownItem(props){
  return(
    <li className = 'dropdownItem'>
      <img src={props.img}></img>
      <a> {props.text} </a>
    </li>
  );
}



export default App;


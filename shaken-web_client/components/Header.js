import React from 'react';

const Header = () => {
    return (
        <div>
            <div className='flex justify-between'>
                <h1>Shaken</h1>
                <div className='flex'>
                    <h3>Login</h3>
                    <h3>Sign Up</h3>
                </div>
            </div>
            <div>
                <h2>By Cocktail</h2>
                <h2>By Ingredient</h2>
            </div>
        </div>
    );
}

export default Header;

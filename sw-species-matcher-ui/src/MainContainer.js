import React, {useState} from 'react';
import logo from './logo.svg';
import ReactJson from 'react-json-view';

const MainContainer = () => {
    const [filmId, setFilmId] = useState("0");
    const [characterId, setCharacterId] = useState("0");
    const [fetching, setFetching] = useState(false);
    const [jsonResponse, setJsonResponse] = useState({});

    const toggleButtonState = () => {
        fetch(`${process.env.REACT_APP_HOST_URL}?film_id=${filmId}&character_id=${characterId}`)
            .then(response => {
                response.json().then(data => setJsonResponse(data));
            })
            .catch(reason => {
                setJsonResponse(({message: reason.message}))
            })
            .finally(() => {
                setFetching(false);
            })
    };

    return (
        <header className="App-header">
            <div>
                <ReactJson name={"response"} src={jsonResponse} enableClipboard={false} displayObjectSize={false} displayDataTypes={false} theme={ReactJsonTheme()}/>
            </div>
            <img src={logo} className="App-logo" alt="logo"/>
            <div>
                <label htmlFor="filmId">Film Id</label>
                <input id="filmId" name="filmId" onChange={e => setFilmId(e.target.value)} type="text"/>

                <label htmlFor="characterId">Character Id</label>
                <input id="characterId" name="characterId" onChange={e => setCharacterId(e.target.value)} type="text"/>

                <button disabled={fetching} onClick={() => toggleButtonState() & setFetching(true)}>Species Matches
                </button>
            </div>
        </header>
    )
};

const ReactJsonTheme = () => {
    return {
        base00: "#000000",
        base01: "#FFE81F",
        base02: "#FFE81F",
        base03: "#FFE81F",
        base04: "#FFE81F",
        base05: "#FFE81F",
        base06: "#FFE81F",
        base07: "#FFE81F",
        base08: "#FFE81F",
        base09: "#FFE81F",
        base0A: "#FFE81F",
        base0B: "#FFE81F",
        base0C: "#FFE81F",
        base0D: "#FFE81F",
        base0E: "#FFE81F",
        base0F: "#FFE81F"
    };
};

export default MainContainer;
'use strict';

import ReactLoading from 'react-loading';

const React = require('react');
const ReactDOM = require('react-dom');
const client = require('./client');
const ReactBootstrap = require('react-bootstrap');
var Button = ReactBootstrap.Button;
var Modal = ReactBootstrap.Modal;

class App extends React.Component {

    constructor(props) {
        super(props);
        this.state = {recipes: []};
    }

    componentDidMount() {
        client({method: 'GET', path: '/api/main/recipes/'}).done(response => {
            this.setState({recipes: response.entity});
        });
    }

    render() {
        return (
            <RecipeList recipes={this.state.recipes}/>
    )
    }
}


class RecipeList extends React.Component{
    render() {
        var recipes = this.props.recipes.map(recipe =>
            <Recipe key={recipe.recipe_id} recipe={recipe}/>
        );
        return (
            <div className="container-full">
                <div className="row row-no-padding">
                    {recipes}
                </div>
            </div>
    )
    }
}


class Recipe extends React.Component{

    constructor(props){
        super(props);
        this.open = this.open.bind(this);
        this.close = this.close.bind(this);
        this.state = { showModal: false, recipeDetail: [], alreadyLoaded: false};
        console.log('Constructor called');
    }

    componentDidUpdate(){
        if(!this.state.alreadyLoaded && this.state.showModal){
            console.log('I was triggered during componentDidUpdate');
            this.setState({ alreadyLoaded: true });
            let detailPath = "/api/yummly/recipe/" + this.props.recipe.id + "?id=" + this.props.recipe.recipe_id;
            console.log(detailPath);
            client({method: 'GET', path: detailPath}).done(response => {
                this.setState({recipeDetail: response.entity});
            });
        }
    }

    getInitialState() {
        return { showModal: false , alreadyLoaded: false};
    }

    close() {
        this.setState({ showModal: false });
    }

    open() {
        console.log('Open show modal triggered');
        this.setState({ showModal: true });
    }

    render() {
        var recipeImage = <img className="img-responsive" src={`${this.props.recipe.imageUrl.replace("=s90-c","=s320-c")}`}/>;
        return (
            <div className="col-xs-6 col-sm-4 col-md-3 col-lg-2 offset-0">
                <a onClick={this.open}>
                    <div className="carousel slide" data-ride="carousel">
                        <div className="carousel-inner">
                            <div className="item active image">
                                {recipeImage}
                                <div className="carousel-caption">
                                    <strong>{this.props.recipe.recipeName}</strong>
                                </div>
                            </div>
                        </div>
                    </div>
                </a>
                <Modal show={this.state.showModal} bsSize="large" onHide={this.close}>
                    <Modal.Header closeButton>
                        <Modal.Title><strong>{this.props.recipe.recipeName}</strong></Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <RecipeDetail key={this.state.recipeDetail.id} recipeDetail={this.state.recipeDetail}  recipeImage={recipeImage}/>
                    </Modal.Body>
                    <Modal.Footer>
                        <Button onClick={this.close}>Close</Button>
                    </Modal.Footer>
                </Modal>
            </div>
    )
    }
}

class RecipeDetail extends React.Component{

    render() {
        var ingredientLines = [];

        if(this.props.recipeDetail === undefined || this.props.recipeDetail.length === 0){
            console.log("recipeDetail is undefined");
            return(
                <div className="container-fluid">
                    <div className="row">
                         <div className="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                            <ReactLoading id="spinningBubbles" type="spinningBubbles" color="#444"/>
                         </div>
                    </div>
                </div>
            )
        } else {
            console.log("recipeDetail is defined");
            var ingredients = Array.from( new Set( this.props.recipeDetail.ingredientLines ) );
            var arrayLength = ingredients.length;
            for(var i=0 ; i < arrayLength ; i++){
                ingredientLines.push(<li key={i}>{ingredients[i]}</li>);
            }

            var recommendedRecipesIngredients = this.props.recipeDetail.recommendedBasedOnIngredients.map(recipe =>
                <RecommendedRecipe key={recipe.recipe_id} recipe={recipe} recommederType="ingredients" parentRecipeId={this.props.key}/>
            );

            var recommendedRecipesFlavors = this.props.recipeDetail.recommendedBasedOnFlavors.map(recipe =>
                <RecommendedRecipe key={recipe.recipe_id} recipe={recipe} recommederType="flavors" parentRecipeId={this.props.key}/>
            );


            var prepTime = []
            var cookingTime = [];

            if(this.props.recipeDetail.prepTime === null || this.props.recipeDetail.prepTime.length === 0 ){
                prepTime = 'Not stated';
            }else{
                prepTime = this.props.recipeDetail.prepTime;
            }

            if(this.props.recipeDetail.cookTime === null || this.props.recipeDetail.cookTime.length === 0 ){
                cookingTime = 'Not stated';
            }else{
                cookingTime = this.props.recipeDetail.cookTime;
            }

            return (
                <div className="container-fluid">
                    <div className="row">
                        <div className="col-lg-4 col-md-4 col-sm-6 col-xs-12">
                            {this.props.recipeImage}
                        </div>
                        <div className="col-lg-8 col-md-8 col-sm-6 col-xs-12">
                            <h4><strong>Ingredients:</strong></h4>
                            <ul>
                                {ingredientLines}
                            </ul>
                            <h4><strong>Preparation time:</strong></h4>
                            <ul>
                                <li>
                                    {prepTime}
                                </li>
                            </ul>
                            <h4><strong>Cooking time:</strong></h4>
                            <ul>
                                <li>
                                    {cookingTime}
                                </li>
                            </ul>
                            <h4><strong>Source of the recipe:</strong></h4>
                            <ul>
                                <li>
                                    <a id="breakText" href={this.props.recipeDetail.sourceRecipe}>{this.props.recipeDetail.sourceRecipe}</a>
                                </li>
                            </ul>
                        </div>
                        <div className="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                            <div className="row top-buffer">
                                <br/>
                                <div className="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                                    <h4><strong>Recommended recipes:</strong></h4>
                                    <hr/>
                                </div>
                                <br/>
                                {recommendedRecipesIngredients}
                                {recommendedRecipesFlavors}
                            </div>
                        </div>
                    </div>
                </div>
            )
        }
    }
}

class RecommendedRecipe extends React.Component{

    constructor(props){
        super(props);
        this.open = this.open.bind(this);
        this.close = this.close.bind(this);
        this.state = { showModal: false, recipeDetail: [], alreadyLoaded: false};
        console.log('Constructor called');
    }

    componentDidUpdate(){
        if(!this.state.alreadyLoaded && this.state.showModal){
            console.log('I was triggered during componentDidUpdate:  showModal:' + this.state.showModal + '  alreadyLoaded:' + this.state.alreadyLoaded);
            this.setState({ alreadyLoaded: true });
            let detailPath = "/api/recipe/recommended/" + this.props.recipe.id + "?recommenderType=" + this.props.recommederType + "&parentRecipeId=" + this.props.parentRecipeId;
            console.log(detailPath);
            client({method: 'GET', path: detailPath}).done(response => {
                this.setState({recipeDetail: response.entity});
            });
        }
    }

    getInitialState() {
        return { showModal: false , alreadyLoaded: false};
    }

    close() {
        this.setState({ showModal: false });
    }

    open() {
        console.log('Open show modal triggered');
        this.setState({ showModal: true });
    }


    render() {
        var recipeImage = <img id="roundedImg" className="img-responsive" src={`${this.props.recipe.imageUrl.replace("=s90-c","=s320-c")}`}/>;
        return (
            <div className="col-xs-6 col-sm-4 col-md-3 col-lg-3 offset-0">
                <a onClick={this.open}>
                    <div className="carousel slide" data-ride="carousel">
                        <div className="carousel-inner">
                            <div className="item active image">
                                {recipeImage}
                                <div className="carousel-caption">
                                    <strong>{this.props.recipe.recipeName}</strong>
                                </div>
                            </div>
                        </div>
                    </div>
                </a>
                <Modal show={this.state.showModal} bsSize="large" onHide={this.close}>
                    <Modal.Header closeButton>
                        <Modal.Title><strong>{this.props.recipe.recipeName}</strong></Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <RecommendedRecipeDetail key={this.state.recipeDetail.id} recipeDetail={this.state.recipeDetail}  recipeImage={recipeImage}/>
                    </Modal.Body>
                    <Modal.Footer>
                        <Button onClick={this.close}>Close</Button>
                    </Modal.Footer>
                </Modal>
            </div>
        )
    }
}


class RecommendedRecipeDetail extends React.Component{

    render() {
        var ingredientLines = [];

        if(this.props.recipeDetail === undefined || this.props.recipeDetail.length === 0){
            console.log("recipeDetail is undefined");
            return(
                <div className="container-fluid">
                    <div className="row">
                        <div className="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                            <ReactLoading id="spinningBubbles" type="spinningBubbles" color="#444"/>
                        </div>
                    </div>
                </div>
            )
        } else {
            console.log("recipeDetail is defined");
            var ingredients = Array.from( new Set( this.props.recipeDetail.ingredientLines ) );
            var arrayLength = ingredients.length;
            for(var i=0 ; i < arrayLength ; i++){
                ingredientLines.push(<li key={i}>{ingredients[i]}</li>);
            }

            var prepTime = []
            var cookingTime = [];

            if(this.props.recipeDetail.prepTime === null || this.props.recipeDetail.prepTime.length === 0 ){
                prepTime = 'Not stated';
            }else{
                prepTime = this.props.recipeDetail.prepTime;
            }

            if(this.props.recipeDetail.cookTime === null || this.props.recipeDetail.cookTime.length === 0 ){
                cookingTime = 'Not stated';
            }else{
                cookingTime = this.props.recipeDetail.cookTime;
            }

            return (
                <div className="container-fluid">
                    <div className="row">
                        <div className="col-lg-4 col-md-4 col-sm-6 col-xs-12">
                            {this.props.recipeImage}
                        </div>
                        <div className="col-lg-8 col-md-8 col-sm-6 col-xs-12">
                            <h4><strong>Ingredients:</strong></h4>
                            <ul>
                                {ingredientLines}
                            </ul>
                            <h4><strong>Preparation time:</strong></h4>
                            <ul>
                                <li>
                                    {prepTime}
                                </li>
                            </ul>
                            <h4><strong>Cooking time:</strong></h4>
                            <ul>
                                <li>
                                    {cookingTime}
                                </li>
                            </ul>
                            <h4><strong>Source of the recipe:</strong></h4>
                            <ul>
                                <li>
                                    <a href={this.props.recipeDetail.sourceRecipe}>{this.props.recipeDetail.sourceRecipe}</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            )
        }
    }
}


ReactDOM.render(
    <App />,
    document.getElementById('react')
)
// This file is required by the index.html file and will
// be executed in the renderer process for that window.
// All of the Node.js APIs are available in this process.

//Declare tile object
//Declare array of tile objects
//north, south, east, west describe tile terrain
//prey declares the type of prey animal found on that tile
//amount declares the number of that specific tile
//tiger declares 0 for no tiger present on tile, 1 for player 1, or 2 for player 2
var tiles= [ {north: , south: , east: , west: , prey: null,		amount: 1, tiger: 0},
			 {north: , south: , east: , west: , prey: null,		amount: 4, tiger: 0},
			 {north: , south: , east: , west: , prey: null, 	amount: 2, tiger: 0},
			 {north: , south: , east: , west: , prey: null,		amount: 1, tiger: 0},
			 {north: , south: , east: , west: , prey: null,		amount: 8, tiger: 0}, 
			 {north: , south: , east: , west: , prey: null,		amount: 9, tiger: 0},
			 {north: , south: , east: , west: , prey: null,		amount: 4, tiger: 0},
			 {north: , south: , east: , west: , prey: null,		amount: 1, tiger: 0},
			 {north: , south: , east: , west: , prey: null,		amount: 4, tiger: 0},
			 {north: , south: , east: , west: , prey: null,		amount: 5, tiger: 0},
			 {north: , south: , east: , west: , prey: null,  	amount: 3, tiger: 0},
			 {north: , south: , east: , west: , prey: null,		amount: 3, tiger: 0},
			 {north: , south: , east: , west: , prey: null, 	amount: 5, tiger: 0},
			 {north: , south: , east: , west: , prey: null, 	amount: 2, tiger: 0},
			 {north: , south: , east: , west: , prey: null,  	amount: 1, tiger: 0},
			 {north: , south: , east: , west: , prey: null, 	amount: 2, tiger: 0},
			 {north: , south: , east: , west: , prey: null, 	amount: 1, tiger: 0},
			 {north: , south: , east: , west: , prey: null, 	amount: 2, tiger: 0},
			 {north: , south: , east: , west: , prey: null, 	amount: 3, tiger: 0},
			 {north: , south: , east: , west: , prey: "deer", 	amount: 2, tiger: 0},
			 {north: , south: , east: , west: , prey: null,   	amount: 3, tiger: 0},
			 {north: , south: , east: , west: , prey: null,		amount: 1, tiger: 0},
			 {north: , south: , east: , west: , prey: "boar",	amount: 2, tiger: 0},
			 {north: , south: , east: , west: , prey: null,		amount: 3, tiger: 0},
			 {north: , south: , east: , west: , prey: "buffalo",amount: 2, tiger: 0},
			 {north: , south: , east: , west: , prey: null, 	amount: 1, tiger: 0},
			 {north: , south: , east: , west: , prey: "deer", 	amount: 2, tiger: 0}];
var p1= {score: 0, turn: false, tigers: 7 };
var p2= {score: 0, turn: false, tigers: 7};

//game trail is equivalent to road
function gametrail_complete(){

	//check for road
}

//lake is equivalent to city
function lake_complete(){

}

//den is equivalent to monastery/cloister
function den_complete(){

}

function near_completed_lake(){
	var lake_found=0;

	return lake_found*3;

}

function near_completed_den(){
	var den_found=0;

	return den_found*5;

}

function surrounding_den(){

}

//jungle is equivalent to farm
//scores points for jungle
function score_jungle(tiles, p1, p2){
	var score=0;
	var player=0;
	score=near_completed_lake() + near_completed_den();
	player= max_tigers(tiles);
	allocate_score(player, score, p1, p2;
};

//counts number of prey animals
function prey(){

}

//counts UNIQUE prey animals
function unique_prey(){



}

//counts tiles in completed terrain
function tile_count(){

}

//counts maximum amount of tiles on a terrain and assigns to player and returns them to player's supply
function max_tigers(tiles){
	//returns 0 if tie, 1 if player 1, or 2 if player 2 
	var max1=0;
	var max2=0;
	for(i=0; i< tiles.length; i++){
		if(tiles[i].tiger==1) max1++;
		else if(tiles[i].tiger==2) max2++;
	};
	if(max1==max2) return 0;
	else if(max1>max2) return 1;
	else return 2;
};

function end_turn_score(current_tile, tiles, p1, p2){
	var score=0;
	var player=0;
	if( (tiles[current_tile].tiger==true) && gametrail_complete()){
		score= (1*tile_count())+prey();
		player= max_tigers(tiles);
		allocate_score(player, score, p1, p2);
	}
	else if( (tiles[current_tile].tiger==true)  && lake_complete()){
		score= (2*tile_count()*(1+unique_prey()));
		player= max_tigers(tiles);
		allocate_score(player, score, p1, p2);
	}
	else if( (tiles[current_tile].tiger==true)  && den_complete()){
		score= 9;
		player=max_tigers(tiles);
		allocate_score(player, score, p1, p2);
	};

//allocates score
function allocate_score(player, score, p1, p2){
		if(player==1) p1.score+=score;
		else if(player==2) p2.score+=score;
		else {
			p1.score+=score;
			p2.score+=score;
		}
};

function end_game_score(tiles, p1, p2){
	score_jungle(tiles);
	var score=0;
	var player=0;
	if( (tiles[current_tile].tiger==true) && gametrail()){
		score= (1*tile_count())+prey();
		player= max_tigers(tiles);
		allocate_score(player, score, p1, p2);
	}
	else if( (tiles[current_tile].tiger==true)  && lake()){
		score= (1*tile_count()*(1+unique_prey()));
		player= max_tigers(tiles);
		allocate_score(player, score, p1, p2);
	}
	else if( (tiles[current_tile].tiger==true)  && den()){
		score= 1 + surrounding_den();
		player=max_tigers(tiles);
		allocate_score(player, score, p1, p2);
	}
};

// function end_turn(p1, p2){

// 	if(p1.turn_over) {
// 		p1.score+=SCORE();
// 		p1.turn= false;
// 	}
// 	else if(p2.turn){
// 		p2.score+=SCORE();
// 		p2.turn= false;
// 	}
// }


function main(){
	var end_game= 76;

	//play starting tile

	while(end_game!=0)
		//Draw and place tile - is it valid
		//Set tiger to tile?

		end_turn_score(current_tile, tiles, p1, p2);
		end_game--;
	};
	end_game_score(p1,p2);

}
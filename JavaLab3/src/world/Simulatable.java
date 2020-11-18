package world;


/**
* @brief Interface to an object that can be simulated in space-time domain
*/
interface Simulatable {
	
	/**
	* @param dt Delta of time
	* @brief Moves time forward by dt
	*/
	void tick(float dt);

}


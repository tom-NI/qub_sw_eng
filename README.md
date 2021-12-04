# Queens University Belfast
## Masters Software Engineering Module - code repository.

The codebase was for the team project in Software Engineering, which was to learn software modelling, engineering, UML, git. Learning outcomes were showcased by producing a command line game modelled off NASAs Artemis Mission.

Languages used: Java, Junit5.

## The requirements;
<em>The game has up to four players, and their names should be entered – the names may represent organisations rather than individuals.  

The players take turns. They throw 2 virtual dice.  Players are told where they have landed and what their obligations or opportunities are.  Where appropriate, they may indicate their choice of action.  For example, by dedicating some of their resources, they may ‘take charge’ of a square no one else owns.  If a player lands on a square no one owns, but they don’t want to take charge of it themselves, it may be offered at the usual cost to another player.  If a player’s resources have changed, the system indicates the reason for the change and announces the player’s new ‘balance’ (e.g. the ‘funds’, ‘credits’ or ‘resources’ that are still available).   

There is a start square, where players pick up their resources (it’s your choice what the ‘resources’ represent – and you should be inventive with the name of the square – this is the equivalent of a ‘Collect X as you pass Go’ square).  There is a square where nothing happens – again, you decide what it is called in your game.      

There are four ‘systems’, two consisting of three adjacent squares or ‘elements’ and two consisting of two adjacent squares or ‘elements’.  Decide what the systems are called and what elements they will include: these should be based on the real Artemis project – that is part of your requirements analysis – but you will have to simplify, merge or omit the details of the real-world project in order to achieve a match with the constraints of your game.  For example, you may (but you don’t have to!) decide that the Space Launch System of the real-world is appropriate as a system in your game too, and that SLS Boosters will be an element of the Space Launch System [say it out loud: it should makes sense!].  One of the two-element systems (in your game at least) is the most costly system to acquire and resource; another two-element system is the least costly system to acquire and resource.  

Before you can develop an element within a system, you must own/manage/’be in charge of’ the whole system (you decide what ‘custodianship’ means!).  On your turn you can develop an element in a system that you already own, even if you are not currently positioned on that element.  Unlike similar board games, with ArtemisLite you may, if you wish, develop one element fully before developing the others, and you may undertake more than one development per turn. 

Decide what a development is called, what it represents, and how much it costs in your game.  Three ‘developments’ of an element are needed before you can complete (and pay for, or otherwise ‘resource’) the equivalent of a ‘major development’ (again, you decide what this represents and what it costs). 

Not only is there a cost associated with developing elements within systems: when you land on an element, but do not ‘own’ it yourself, you normally have to give up some of your resources for it – the more developed the element, the greater the resource that you are normally expected to give the owner.  However, in the interests of the greater good, the owner may decline to accept your offer of resources.  Why would that be…? 

Well, if one player runs out of resources during the gameplay, or if one player no longer wants to play, the game ends for all the players.  Is the player without resources the loser, or have they heroically given their all in the noble cause of space exploration?  You decide.  When the game ends, show the final state of play: who held which squares, how developed were they, and what other resources did the players hold? There is no need to convert ‘developments’, etc., to an equivalent value in your ‘resource units’. 

On the other hand, if all the elements of all the systems are fully developed, Artemis is ready for launch: all systems are ‘go’ and the project is on the path to ultimate success!  It pays, in other words, to keep people in the game, let them acquire and develop systems, and together complete the mission!  As soon as development is complete, announce the path ahead: this will be like a summary of future events at the end of a movie (an epilogue).   Display the successful outcome dynamically as a sequence of headlines: e.g. in 2021[…], then in 2022 […] until finally a successful landing is achieved, with congratulations all round!  Also give the final state of play that made it possible. <em>

# MiniDex
A very basic implementation of PokeAPI (https://pokeapi.co) in Java.
It's the base code that's used for the Pokedex functions in CharizardBot (https://charizardbot.com/).

Uses org.json library and Maven.

It can accept either a number or a name.
Names must be lowercase.
Example use:

```
	public static void main(String[] args) {
		String stats = MiniDex.getPokemonInfo("pikachu");
		String pkName = MiniDex.getPokemonName();
		String pkSprite = MiniDex.getSprite();
		System.out.println("Name: " + pkName);
		System.out.println("Sprite: " + pkSprite);
		System.out.println("Stats:\n" + stats);
	}
```

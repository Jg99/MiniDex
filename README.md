# MiniDex
A very basic implementation of PokeAPI for Java.
It's the base code that's used for the Pokedex functions in CharizardBot (https://charizardbot.com/).

Uses org.json library and Maven.

Example use:

```
	public static void main(String[] args) {
		String stats = MiniDex.getPokemonInfo("Pikachu");
		String pkName = MiniDex.getPokemonName();
		String pkSprite = MiniDex.getSprite();
		System.out.println("Name: " + pkName);
		System.out.println("Sprite: " + pkSprite);
		System.out.println("Stats:\n" + stats);
	}
```

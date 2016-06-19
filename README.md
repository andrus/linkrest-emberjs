# linkrest-emberjs

Provides a LinkRest adapter to serve EmberJS-compatible responses.

## Missing Features:

* Sideloaded relationships.
* Differentiate between collections and and single objects.
  (though EmberJS docs imply we can use collection structure and Ember will recognize it).

## Getting Started

_Requires Java 8 and LinkRest 2.1 or newer_ 

Add dependency on linkrest-emberjs:

```xml
<dependency>
    <groupId>org.objectstyle.linkrest.emberjs</groupId>
    <artifactId>linkrest-emberjs</artifactId>
    <version>0.1-SNAPSHOT</version>
</dependency>
```
_TODO: we don't publish this to central yet, so you will have to fork/clone this code and
build it locally before using as a dependency._

## Using in a LinkRest App

```java
LinkRestBuilder.builder(cayenneRuntime).adapter(new EmberJSAdapter()).build();
```

## Using in a LinkRest Bootique App

If you are using Bootique, you'd integrate the adapter in your module's ```configure``` method:

```java
@Override
public void configure(Binder binder) {
    LinkRestModule.contributeAdapters(binder).addBinding().to(EmberJSAdapter.class);
}
```

Check out included ```linkrest-emberjs-example``` for a fully working Bootique app with an embedded Derby DB.
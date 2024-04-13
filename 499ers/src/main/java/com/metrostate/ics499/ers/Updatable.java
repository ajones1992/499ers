package com.metrostate.ics499.ers;

/**
 * This interface signals that a class is updatable in a database through the DBAdapter.
 *
 * @param <T> the datatype that will be updatable
 */
public interface Updatable<T> {

    /**
     * Updates this instance of T object to the new update version. Returns true
     * if the original object was updated successfully; false otherwise.
     *
     * @param update the updated object
     * @return true if the update succeeds; false otherwise
     */
    public abstract boolean update(T update);

}

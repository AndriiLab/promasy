package com.github.andriilab.promasy.testutil;

import javax.persistence.EntityTransaction;

/**
 * Minimal state machine behaving like a JPA resource-local transaction: {@code begin} on an active transaction
 * throws, as it does on the real shared {@code EntityManager}.
 */
public class FakeTransaction implements EntityTransaction {

    private boolean active;
    private int commits;
    private int rollbacks;

    @Override
    public void begin() {
        if (active) {
            throw new IllegalStateException("Transaction already active");
        }
        active = true;
    }

    @Override
    public void commit() {
        requireActive();
        active = false;
        commits++;
    }

    @Override
    public void rollback() {
        requireActive();
        active = false;
        rollbacks++;
    }

    @Override
    public void setRollbackOnly() {
        requireActive();
    }

    @Override
    public boolean getRollbackOnly() {
        requireActive();
        return false;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    public int getCommits() {
        return commits;
    }

    public int getRollbacks() {
        return rollbacks;
    }

    private void requireActive() {
        if (!active) {
            throw new IllegalStateException("Transaction not active");
        }
    }
}

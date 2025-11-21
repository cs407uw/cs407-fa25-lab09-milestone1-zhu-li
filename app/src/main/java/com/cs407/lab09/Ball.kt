package com.cs407.lab09

/**
 * Represents a ball that can move. (No Android UI imports!)
 *
 * Constructor parameters:
 * - backgroundWidth: the width of the background, of type Float
 * - backgroundHeight: the height of the background, of type Float
 * - ballSize: the width/height of the ball, of type Float
 */
class Ball(
    private val backgroundWidth: Float,
    private val backgroundHeight: Float,
    private val ballSize: Float
) {
    var posX = 0f
    var posY = 0f
    var velocityX = 0f
    var velocityY = 0f
    private var accX = 0f
    private var accY = 0f

    private var isFirstUpdate = true

    init {
        reset()
    }

    /**
     * Updates the ball's position and velocity based on the given acceleration and time step.
     * (See lab handout for physics equations)
     */
    fun updatePositionAndVelocity(xAcc: Float, yAcc: Float, dT: Float) {
        if (isFirstUpdate) {
            isFirstUpdate = false
            accX = xAcc
            accY = yAcc
            return
        }

        if (dT <= 0f) {
            accX = xAcc
            accY = yAcc
            return
        }

        val speedFactor = 40f   // try 20f, 40f, 60f and tune

        // a0: previous acceleration (scaled)
        val a0x = accX * speedFactor
        val a0y = accY * speedFactor

        // a1: new acceleration (scaled)
        val a1x = xAcc * speedFactor
        val a1y = yAcc * speedFactor

        // ----- Equation (1): new velocity -----
        val newVelX = velocityX + 0.5f * (a1x + a0x) * dT
        val newVelY = velocityY + 0.5f * (a1y + a0y) * dT

        // ----- Equation (2): distance traveled -----
        val dt2 = dT * dT
        val dx = velocityX * dT + (1f / 6f) * dt2 * (3f * a0x + a1x)
        val dy = velocityY * dT + (1f / 6f) * dt2 * (3f * a0y + a1y)

        // Apply displacement
        posX += dx
        posY += dy

        // Store new velocity, but keep *raw* acceleration for next step
        velocityX = newVelX
        velocityY = newVelY
        accX = xAcc
        accY = yAcc

        // Keep ball inside field
        checkBoundaries()
    }

    /**
     * Ensures the ball does not move outside the boundaries.
     * When it collides, velocity and acceleration perpendicular to the
     * boundary should be set to 0.
     */
    fun checkBoundaries() {
        // TODO: implement the checkBoundaries function
        // (Check all 4 walls: left, right, top, bottom)
        // Horizontal: left and right walls
        val maxX = backgroundWidth - ballSize
        if (posX < 0f) {
            posX = 0f
            velocityX = 0f
            accX = 0f
        } else if (posX > maxX) {
            posX = maxX
            velocityX = 0f
            accX = 0f
        }

        // Vertical: top and bottom walls
        val maxY = backgroundHeight - ballSize
        if (posY < 0f) {
            posY = 0f
            velocityY = 0f
            accY = 0f
        } else if (posY > maxY) {
            posY = maxY
            velocityY = 0f
            accY = 0f
        }
    }

    /**
     * Resets the ball to the center of the screen with zero
     * velocity and acceleration.
     */
    fun reset() {
        // TODO: implement the reset function
        // (Reset posX, posY, velocityX, velocityY, accX, accY, isFirstUpdate)
        posX = (backgroundWidth - ballSize) / 2f
        posY = (backgroundHeight - ballSize) / 2f
        velocityX = 0f
        velocityY = 0f
        accX = 0f
        accY = 0f
        isFirstUpdate = true
    }
}
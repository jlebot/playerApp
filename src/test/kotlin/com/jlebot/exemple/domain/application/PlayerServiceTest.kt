package com.jlebot.exemple.domain.application

import com.jlebot.exemple.domain.api.IPlayerApi
import com.jlebot.exemple.domain.api.Player
import com.jlebot.exemple.exception.PlayerAlreadyExistException
import com.jlebot.exemple.exception.PlayerNotFoundException
import com.jlebot.exemple.exception.PlayerValidationException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito

class PlayerServiceTest() {

    @Mock
    private val playerRepository: IPlayerRepository = Mockito.mock(IPlayerRepository::class.java)

    val playerApi: IPlayerApi = PlayerService(playerRepository)

    @Test
    @Throws(PlayerNotFoundException::class)
    fun testGetPlayerThrowExceptionWhenPlayerNotFound() {
        // ARRANGE
        val playerUnknown = "Toto"
        Mockito.`when`(playerRepository.find(playerUnknown)).thenReturn(null)

        // ACT and ASSERT
        assertThrows(PlayerNotFoundException::class.java) {
            playerApi.getPlayer(playerUnknown)
        }
    }

    @Test
    fun testCreateWithoutError() {
        // ARRANGE
        val player = "NewPlayer"

        // ACT
        val result = playerApi.create(player)

        // ASSERT
        assertNotNull(result)
        assertEquals(player, result.pseudo)
        assertEquals(0, result.points)
    }

    @Test
    @Throws(PlayerAlreadyExistException::class)
    fun testCreatePlayerThrowExceptionWhenPlayerAlreadyExist() {
        // ARRANGE
        val existingPlayer = "M3rzhin"
        Mockito.`when`(playerRepository.find(existingPlayer)).thenReturn(Player(existingPlayer, 0))

        // ACT and ASSERT
        assertThrows(PlayerAlreadyExistException::class.java) {
            playerApi.create(existingPlayer)
        }
    }

    @Test
    fun testSaveWithoutError() {
        // ARRANGE
        val player = Player("M3rzhin", 666)

        // ACT
        val result = playerApi.save(player)

        // ASSERT
        assertNotNull(result)
    }

    @Test
    @Throws(PlayerValidationException::class)
    fun testSaveThrowExceptionWhenPseudoNotSet() {
        // ARRANGE
        val playerWithEmptyPseudo = Player("", 0)

        // ACT and ASSERT
        assertThrows(PlayerValidationException::class.java) {
            playerApi.save(playerWithEmptyPseudo)
        }
    }

    @Test
    @Throws(PlayerValidationException::class)
    fun testSaveThrowExceptionWhenPseudoLenghtLimitExceed() {
        // ARRANGE
        val playerWithTooLongPseudo = Player("", 0)

        // ACT and ASSERT
        assertThrows(PlayerValidationException::class.java) {
            playerApi.save(playerWithTooLongPseudo)
        }
    }

    @Test
    @Throws(PlayerValidationException::class)
    fun testSaveThrowExceptionWhenPseudoNotAlphaNumeric() {
        // ARRANGE
        val playerWithNotAlphaNumericPseudo= Player("a b", 0)

        // ACT and ASSERT
        assertThrows(PlayerValidationException::class.java) {
            playerApi.save(playerWithNotAlphaNumericPseudo)
        }
    }

}
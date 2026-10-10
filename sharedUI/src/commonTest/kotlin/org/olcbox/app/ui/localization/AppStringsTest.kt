package org.olcbox.app.ui.localization

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class AppStringsTest {

    @Test
    fun testPingIsPreservedWithoutTranslation() {
        assertEquals("Ping", EnAppStrings.pingButtonLabel)
        assertEquals("Ping", RuAppStrings.pingButtonLabel)
    }

    @Test
    fun testEstablishedTermsArePreservedInRussian() {
        assertTrue(RuAppStrings.socks5Proxy.contains("SOCKS5"))
        assertTrue(RuAppStrings.routingModeTun.contains("TUN"))
        assertTrue(RuAppStrings.tracksLabel.contains("MIMO"))
        assertTrue(RuAppStrings.vp8Options.contains("VP8"))
        assertTrue(RuAppStrings.connectionTypeJitsi.contains("Jitsi"))
        assertTrue(RuAppStrings.fpsLabel.contains("FPS"))
    }

    @Test
    fun testPrimaryActionsAreConcise() {
        assertEquals("СТАРТ", RuAppStrings.start)
        assertEquals("СТОП", RuAppStrings.stop)
        assertEquals("НАСТРОЙКА", RuAppStrings.setup)
        assertEquals("Офлайн", RuAppStrings.offline)

        // Russian strings should not be excessively long compared to English
        assertTrue(RuAppStrings.start.length <= EnAppStrings.start.length + 2)
        assertTrue(RuAppStrings.stop.length <= EnAppStrings.stop.length + 2)
    }

    @Test
    fun testRussianPluralization() {
        assertEquals("1 подписка", RuAppStrings.subscriptionsCount(1))
        assertEquals("2 подписки", RuAppStrings.subscriptionsCount(2))
        assertEquals("5 подписок", RuAppStrings.subscriptionsCount(5))
        assertEquals("11 подписок", RuAppStrings.subscriptionsCount(11))
        assertEquals("21 подписка", RuAppStrings.subscriptionsCount(21))

        assertEquals("1 локация", RuAppStrings.locationsCount(1))
        assertEquals("3 локации", RuAppStrings.locationsCount(3))
        assertEquals("10 локаций", RuAppStrings.locationsCount(10))
    }

    @Test
    fun testLanguageSelection() {
        assertEquals(RuAppStrings, appStringsFor(AppLanguage.Russian))
        assertEquals(EnAppStrings, appStringsFor(AppLanguage.English))
    }
}

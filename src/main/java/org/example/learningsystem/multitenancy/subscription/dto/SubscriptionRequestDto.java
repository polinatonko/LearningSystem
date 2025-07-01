package org.example.learningsystem.multitenancy.subscription.dto;

/**
 * Represents data transmitted by subscription to a multi-tenant application callback.
 *
 * @param subscriptionAppId     the application ID of the main subscribed application
 * @param subscriptionAppName   the application name of the main subscribed application
 * @param subscribedTenantId    ID of the subscription tenant
 * @param subscribedSubdomain   the subdomain of the subscription tenant (hostname for the identity zone)
 * @param globalAccountGUID     ID of the global account
 * @param subscribedLicenseType the license type of the subscription tenant
 */
public record SubscriptionRequestDto(

        String subscriptionAppId,

        String subscriptionAppName,

        String subscribedTenantId,

        String subscribedSubdomain,

        String globalAccountGUID,

        String subscribedLicenseType
) {
}
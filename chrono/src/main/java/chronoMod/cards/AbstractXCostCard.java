package chronoMod.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public abstract class AbstractXCostCard extends AbstractDynamicCard {
    private static final int COST = -1;

    public AbstractXCostCard(final String id, final String img, final CardType type, final CardColor color,
                             final CardRarity rarity, final CardTarget target) {
        super(id, img, COST, type, color, rarity, target);
    }

    public int getEffectNum(AbstractPlayer p) {
        int effect = EnergyPanel.totalCount;
        if (this.energyOnUse != -1) {
            effect = this.energyOnUse;
        }
        if (p.hasRelic("Chemical X")) {
            effect += 2;
            p.getRelic("Chemical X").flash();
        }
        // Use energy
        if (!this.freeToPlayOnce) {
            p.energy.use(EnergyPanel.totalCount);
        }
        return effect;
    }
}

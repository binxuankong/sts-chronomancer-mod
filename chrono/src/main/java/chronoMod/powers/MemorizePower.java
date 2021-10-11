package chronoMod.powers;

import chronoMod.ChronoMod;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class MemorizePower extends RecallPower {
    public static final String POWER_ID = ChronoMod.makeID("Memorize");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private AbstractCard card;
    private static int idOffset;

    public MemorizePower(AbstractCreature owner, AbstractCard card) {
        super(owner);
        this.name = NAME;
        this.ID = POWER_ID + idOffset;
        idOffset++;
        this.card = card;
        this.updateDescription();
        this.loadRegion("wireheading");
    }

    @Override
    public void recallEffect () {
        // card.setCostForTurn(0);
        card.freeToPlayOnce = true;
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.card.name + DESCRIPTIONS[1];
    }

    public AbstractPower makeCopy() {
        return new MemorizePower(this.owner, this.card);
    }
}

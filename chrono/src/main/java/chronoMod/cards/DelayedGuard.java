package chronoMod.cards;

import chronoMod.ChronoMod;
import chronoMod.characters.Chronomancer;
import chronoMod.powers.RecallBlockPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static chronoMod.ChronoMod.makeCardPath;

public class DelayedGuard extends AbstractDynamicCard {
    private static final String CARD_ID = DelayedGuard.class.getSimpleName();
    public static final String ID = ChronoMod.makeID(CARD_ID);
    public static final String IMG = makeCardPath("skill/" + CARD_ID + ".png");

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Chronomancer.Enums.COLOR_BLUE;

    private static final int COST = 0;
    private static final int BLOCK = 6;
    private static final int UPGRADE_PLUS_BLOCK = 3;

    public DelayedGuard() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseBlock = BLOCK;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(p, p, new RecallBlockPower(p, this.block), this.block));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            initializeDescription();
        }
    }

    @Override
    public AbstractDynamicCard makeCopy() {
        return new DelayedGuard();
    }
}
